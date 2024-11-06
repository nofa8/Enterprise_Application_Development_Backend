package adgf.academics.exceptions.mappers;

import adgf.academics.exceptions.MyEntityExistsException;
import adgf.academics.exceptions.MyEntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class MyEntityNotFoundExceptionMapper implements ExceptionMapper<MyEntityNotFoundException> {
    private static final Logger logger=Logger.getLogger(
            adgf.academics.exceptions.MyEntityNotFoundException.class.getName());

    @Override
    public Response toResponse(MyEntityNotFoundException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.CONFLICT).entity(errorMsg).build();

    }

}