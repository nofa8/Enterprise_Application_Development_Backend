package adgf.academics.exceptions.mappers;

import jakarta.ws.rs.core.Response;

import java.util.logging.Logger;

public class ExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {
    private static final Logger logger=Logger.getLogger(Exception.class.getName());

    @Override
    public Response toResponse(Exception e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMsg).build();
    }
}
