package com.hashedin.exceptions;

import com.hashedin.constants.Constants;
import com.hashedin.dto.ResponseDto;
import com.hashedin.utils.SignUpUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NoDataExists.class)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public <T> ResponseDto<T> handleNoDataException(NoDataExists e) {
        String exceptionId = getExceptionId();
        log.error("Got NoRecordException for exceptionId: {} with Message: {}", exceptionId, e);
        return ResponseDto.failure(e.getMessage(), exceptionId);
    }

    @ExceptionHandler(Forbidden.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public <T> ResponseDto<T> handleForbiddenException(Forbidden e) {
        String exceptionId = getExceptionId();
        log.error("Forbidden user with exceptionId: {} with Message: {}", exceptionId, e);
        return ResponseDto.failure(e.getMessage(), exceptionId);
    }

    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public <T> ResponseDto<T> handleBadRequestException(Forbidden e) {
        String exceptionId = getExceptionId();
        log.error("Bad Request for the user with exceptionId: {} with Message: {}", exceptionId, e);
        return ResponseDto.failure(e.getMessage(), exceptionId);
    }

    @ExceptionHandler(DataExistsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public <T> ResponseDto<T> handleDataExistsException(Forbidden e) {
        String exceptionId = getExceptionId();
        log.error("Data Exists already for the user with exceptionId: {} with Message: {}", exceptionId, e);
        return ResponseDto.failure(e.getMessage(), exceptionId);
    }

    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public <T> ResponseDto<T> handleInvalidExceptionException(Forbidden e) {
        String exceptionId = getExceptionId();
        log.error("Handle Invalid data with exceptionId: {} with Message: {}", exceptionId, e);
        return ResponseDto.failure(e.getMessage(), exceptionId);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public <T> ResponseDto<T> handleRecordNotFoundException(Forbidden e) {
        String exceptionId = getExceptionId();
        log.error("Record Not Found for the user with exceptionId: {} with Message: {}", exceptionId, e);
        return ResponseDto.failure(e.getMessage(), exceptionId);
    }

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public <T> ResponseDto<T> handleTokenException(Forbidden e) {
        String exceptionId = getExceptionId();
        log.error("Token Exception for the user with exceptionId: {} with Message: {}", exceptionId, e);
        return ResponseDto.failure(e.getMessage(), exceptionId);
    }

    @ExceptionHandler(Unauthorized.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public <T> ResponseDto<T> handleUnaouthorizedException(Forbidden e) {
        String exceptionId = getExceptionId();
        log.error("Unauthorised user with exceptionId: {} with Message: {}", exceptionId, e);
        return ResponseDto.failure(e.getMessage(), exceptionId);
    }

    //Generating exception Id
    private String getExceptionId() {
        String exceptionId = MDC.get(Constants.GUID);
        if (StringUtils.isBlank(exceptionId)) {
            exceptionId = SignUpUtils.generateUniqueId();
        }
        return exceptionId;
    }
}
