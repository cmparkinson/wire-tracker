/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.controller;

import ca.parkie.wiring.client.JSOConstants;
import ca.parkie.wiring.dto.DataTransferObject;
import ca.parkie.wiring.dto.client.ClientPersistDTO;
import ca.parkie.wiring.entity.PersistentEntity;
import ca.parkie.wiring.service.*;
import com.smartgwt.client.rpc.RPCResponse;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaOptimisticLockingFailureException;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractRestController
{
    private enum PersistType
    {
        INSERT, UPDATE, REMOVE
    }

    @Autowired
    private DozerBeanMapper dozerMapper;

    @Autowired
    private RackService rackService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private NetworkInterfaceCardService nicService;

    @Autowired
    private PatchPanelService patchPanelService;
    
    @Autowired
    private ServerService serverService;

    @Autowired
    private SwitchService switchService;

    public DozerBeanMapper getDozerMapper()
    {
        return dozerMapper;
    }

    public RackService getRackService()
    {
        return rackService;
    }

    public ItemService getItemService()
    {
        return itemService;
    }

    public NetworkInterfaceCardService getNetworkInterfaceCardService()
    {
        return nicService;
    }

    public PatchPanelService getPatchPanelService()
    {
        return patchPanelService;
    }

    public ServerService getServerService()
    {
        return serverService;
    }

    public SwitchService getSwitchService()
    {
        return switchService;
    }

    protected <D extends DataTransferObject, T extends PersistentEntity> T dtoToEntity(ClientPersistDTO<D> dto,
                                                                                       Class<T> entityClass)
    {
        D entityDto = dto.getNewValues();
        return getDozerMapper().map(entityDto, entityClass);
    }


    // Remove Object
    protected <D extends DataTransferObject, T extends PersistentEntity> Map<String, Object> removeObject(
            T entity,
            Class<D> dtoClass,
            Validator<T> validator,
            Service<T> service)
    {
        return persist(PersistType.REMOVE, entity, dtoClass, validator, service);
    }

    // Insert Object
    protected <D extends DataTransferObject, T extends PersistentEntity> Map<String, Object> insertObject(
            T entity,
            Class<D> dtoClass,
            Validator<T> validator,
            Service<T> service)
    {
        return persist(PersistType.INSERT, entity, dtoClass, validator, service);
    }

    // Update object
    protected <D extends DataTransferObject, T extends PersistentEntity> Map<String, Object> updateObject(
            T entity,
            Class<D> dtoClass,
            Validator<T> validator,
            Service<T> service)
    {
        return persist(PersistType.UPDATE, entity, dtoClass, validator, service);
    }

    // Generic persistence code for URD operations
    private <D extends DataTransferObject, T extends PersistentEntity> Map<String, Object> persist(PersistType type,
                                                                                                   T entity,
                                                                                                   Class<D> dtoClass,
                                                                                                   Validator<T> validator,
                                                                                                   Service<T> service)
    {
        // Initialize state
        int status = RPCResponse.STATUS_SUCCESS;
        String globalError = null;

        D dto = null;

        boolean refreshObject = false;

        Map<String, String> errors = new HashMap<String, String>();
        Map<String, Object> extensions = new HashMap<String, Object>();

        // Validate the requested action before committing to the database
        if (validator != null)
        {
            switch (type)
            {
                case REMOVE:
                    if (!validator.validateObject(entity))
                    {
                        String message = validator.getMessage();
                        return buildPersistResponse(RPCResponse.STATUS_FAILURE, null, message, null, null);
                    }
                    break;

                case UPDATE:
                case INSERT:
                    if (!validator.validateObject(entity, errors))
                        return buildPersistResponse(RPCResponse.STATUS_VALIDATION_ERROR, null, null, errors, extensions);
                    break;
            }
        }

        try
        {
            switch (type)
            {
                case REMOVE:
                    service.removeEntity(entity);
                    break;
                case INSERT:
                case UPDATE:
                    entity = service.saveEntity(entity);
                    break;
            }
        }
        catch (JpaOptimisticLockingFailureException e)
        {
            status = -1;
            // TODO Localize
            globalError = "The operation failed. The object had been updated or removed since it was last read. "
                    + "The local cache has been refreshed, please check the updated values and try again.";
            refreshObject = true;
        }
        catch (Exception e)
        {
            status = -1;
            // TODO Localize
            globalError = "An unexpected error has occurred.  Please check the server logs.";

            // TODO Log properly
            e.printStackTrace();
        }

        if (refreshObject)
        {
            try
            {
                T fetched = service.retrieveEntity(entity.getId());
                D refreshDto = getDozerMapper().map(fetched, dtoClass);
                extensions.put(JSOConstants.REFRESH, refreshDto);
            }
            catch (EmptyResultDataAccessException e)
            {
                // The object has been deleted.  If this was a remove, return success
                if (type == PersistType.REMOVE)
                {
                    status = RPCResponse.STATUS_SUCCESS;
                    globalError = null;
                }
                else
                {
                    extensions.put(JSOConstants.DELETED, true);
                    globalError = "The requested operation could not be completed. "
                            + "The object has been deleted from the database since it was last read.";
                }
            }
            catch (Exception e)
            {
                globalError = "The requested operation could not be completed. The object has been updated or removed since it was last read. "
                        + "A refresh was requested, however there was a database error while trying to retrieve the object. "
                        + "Please check the server logs.";
            }
        }
        else
        {
            // Map the successfully updated entity back to it's DTO counterpart for transmission back to the client
            dto = getDozerMapper().map(entity, dtoClass);
        }

        return buildPersistResponse(status, dto, globalError, errors, extensions);
    }

    private Map<String, Object> buildPersistResponse(int status,
                                                     Object data,
                                                     String globalError,
                                                     Map<String, String> errors,
                                                     Map<String, Object> extensions)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(JSOConstants.STATUS, status);

        if (globalError != null)
            map.put(JSOConstants.DATA, globalError);
        else
            map.put(JSOConstants.DATA, data);

        if (errors != null)
            map.put(JSOConstants.ERRORS, errors);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put(JSOConstants.RESPONSE, map);

        if (extensions != null && extensions.size() > 0)
            response.put(JSOConstants.EXTENSIONS, extensions);

        return response;
    }

    /* TODO Remove

    protected <D extends DataTransferObject, T extends PersistentEntity> Map<String, Object> removeObject(
            ClientPersistDTO<D> clientDto,
            Class<D> dtoClass,
            Class<T> entityClass,
            Service<T> service,
            Validator<T> validator)
    {
        return persist(PersistType.REMOVE, clientDto, dtoClass, entityClass, service, validator);
    }

    private <D extends DataTransferObject, T extends PersistentEntity> Map<String, Object> persist(
            PersistType type,
            ClientPersistDTO<D> clientDto,
            Class<D> dtoClass,
            Class<T> entityClass,
            Service<T> service,
            Validator<T> validator)
    {
        // Get the values transferred from the client
        D entityDto = clientDto.getNewValues();

        T entity = getDozerMapper().map(entityDto, entityClass);

        // Initialize state
        int status = RPCResponse.STATUS_SUCCESS;
        String globalError = null;

        boolean refreshObject = false;

        Map<String, String> errors = new HashMap<String, String>();
        Map<String, Object> extensions = new HashMap<String, Object>();

        // Validate the requested action before committing to the database
        if (validator != null)
        {
            switch (type)
            {
                case REMOVE:
                    if (!validator.validateObject(entity))
                    {
                        String message = validator.getMessage();
                        return buildPersistResponse(RPCResponse.STATUS_FAILURE, null, message, null, null);
                    }
                    break;

                case UPDATE:
                case INSERT:
                    if (!validator.validateObject(entity, errors))
                        return buildPersistResponse(RPCResponse.STATUS_VALIDATION_ERROR, null, null, errors, extensions);
                    break;
            }
        }

        try
        {
            switch (type)
            {
                case REMOVE:
                    service.removeEntity(entity);
                    break;
                case INSERT:
                case UPDATE:
                    entity = service.saveEntity(entity);
                    break;
            }
        }
        catch (JpaOptimisticLockingFailureException e)
        {
            status = -1;
            // TODO Localize
            globalError = "The operation failed. The object had been updated or removed since it was last read. "
                    + "The local cache has been refreshed, please check the updated values and try again.";
            refreshObject = true;
        }
        catch (Exception e)
        {
            status = -1;
            // TODO Localize
            globalError = "An unexpected error has occurred.  Please check the server logs.";
            e.printStackTrace();
        }

        if (refreshObject)
        {
            try
            {
                T fetched = service.retrieveEntity(entityDto.getId());
                D dto = getDozerMapper().map(fetched, dtoClass);
                extensions.put(JSOConstants.REFRESH, dto);
            }
            catch (EmptyResultDataAccessException e)
            {
                // The object has been deleted.  If this was a remove, return success
                if (type == PersistType.REMOVE)
                {
                    status = RPCResponse.STATUS_SUCCESS;
                    globalError = null;
                }
                else
                {
                    extensions.put(JSOConstants.DELETED, true);
                    globalError = "The requested operation could not be completed. "
                            + "The object has been deleted from the database since it was last read.";
                }
            }
            catch (Exception e)
            {
                globalError = "The requested operation could not be completed. The object has been updated or removed since it was last read. "
                        + "A refresh was requested, however there was a database error while trying to retrieve the object. "
                        + "Please check the server logs.";
            }
        }
        else
        {
            // Map the successfully updated entity back to it's DTO counterpart for transmission back to the client
            entityDto = getDozerMapper().map(entity, dtoClass);
        }

        return buildPersistResponse(status, entityDto, globalError, errors, extensions);

    }

    protected <T extends PersistentEntity, D extends DataTransferObject> Map<String, Object> saveOrUpdate(
            T entity,
            Class<D> dtoClass,
            Validator<T> validator,
            Service<T> service)
    {
        int status = RPCResponse.STATUS_SUCCESS;
        String globalError = null;

        boolean refreshObject = false;

        Map<String, String> errors = new HashMap<String, String>();
        Map<String, Object> extensions = new HashMap<String, Object>();
        D dto;

        if (validator != null)
            if (!validator.validateObject(entity, errors))
                return buildPersistResponse(RPCResponse.STATUS_VALIDATION_ERROR, null, null, errors, extensions);

        try
        {
            entity = service.saveEntity(entity);
        }
        catch (JpaOptimisticLockingFailureException e)
        {
            status = -1;
            // TODO Localize
            globalError = "The save operation failed. The object has been updated or removed since it was last read. "
                    + "The object has been refreshed, please try again.";
            refreshObject = true;
        }
        catch (Exception e)
        {
            status = -1;
            // TODO Localize
            globalError = "An unexpected error has occurred.  Please check the server logs.";

            // TODO Log this properly
            e.printStackTrace();
        }

        if (refreshObject)
        {
            try
            {
                T fetched = service.retrieveEntity(entity.getId());
                D refreshDto = getDozerMapper().map(fetched, dtoClass);
                extensions.put(JSOConstants.REFRESH, refreshDto);
            }
            catch (EmptyResultDataAccessException e)
            {
                // The object has been deleted
                extensions.put(JSOConstants.DELETED, true);
                globalError = "The requested update could not be completed. "
                        + "The object has been deleted from the database since it was last read.";
            }
            catch (Exception e)
            {
                globalError = "The requested update could not be completed. The object has been updated or removed since it was last read. "
                        + "A refresh was requested, however there was a database error while trying to retrieve the object. "
                        + "Please check the server logs.";
            }
        }
        else
        {
            // Map the successfully updated entity back to it's DTO counterpart for transmission back to the client
            dto = getDozerMapper().map(entity, dtoClass);
        }

        return buildPersistResponse(status, dto, globalError, errors, extensions);

    }

    protected <D extends DataTransferObject, T extends PersistentEntity> Map<String, Object> saveOrUpdateObject(
            ClientPersistDTO<D> clientDto,
            Class<D> dtoClass,
            Class<T> entityClass,
            Service<T> service,
            Validator<T> validator)
    {
        D entityDto = clientDto.getNewValues();
        T entity = getDozerMapper().map(entityDto, entityClass);

        int status = RPCResponse.STATUS_SUCCESS;
        String globalError = null;

        boolean refreshObject = false;

        Map<String, String> errors = new HashMap<String, String>();
        Map<String, Object> extensions = new HashMap<String, Object>();

        if (validator != null)
            if (!validator.validateObject(entity, errors))
                return buildPersistResponse(RPCResponse.STATUS_VALIDATION_ERROR, null, null, errors, extensions);

        try
        {
            entity = service.saveEntity(entity);
        }
        catch (JpaOptimisticLockingFailureException e)
        {
            status = -1;
            // TODO Localize
            globalError = "The save operation failed. The object has been updated or removed since it was last read. "
                    + "The object has been refreshed, please try again.";
            refreshObject = true;
        }
        catch (Exception e)
        {
            status = -1;
            // TODO Localize
            globalError = "An unexpected error has occurred.  Please check the server logs.";
            e.printStackTrace();
        }

        if (refreshObject)
        {
            try
            {
                T fetched = service.retrieveEntity(entityDto.getId());
                D dto = getDozerMapper().map(fetched, dtoClass);
                extensions.put(JSOConstants.REFRESH, dto);
            }
            catch (EmptyResultDataAccessException e)
            {
                // The object has been deleted
                extensions.put(JSOConstants.DELETED, true);
                globalError = "The requested update could not be completed. "
                        + "The object has been deleted from the database since it was last read.";
            }
            catch (Exception e)
            {
                globalError = "The requested update could not be completed. The object has been updated or removed since it was last read. "
                        + "A refresh was requested, however there was a database error while trying to retrieve the object. "
                        + "Please check the server logs.";
            }
        }
        else
        {
            // Map the successfully updated entity back to it's DTO counterpart for transmission back to the client
            entityDto = getDozerMapper().map(entity, dtoClass);
        }

        return buildPersistResponse(status, entityDto, globalError, errors, extensions);
    }
    */
}
