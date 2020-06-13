package com.karl.base.handler;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.collect.Lists;
import com.karl.base.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Comparator;
import java.util.List;

/**
 * 全局异常接管
 * 注意：
 * 启用全局异常接管后，没有在此处定义拦截的异常都会默认返回500错误。
 * 若需要自定义拦截的异常，请在此处定义拦截。
 * 若需要输出异常的日志日志，请使用 log输出。
 *
 * @author karl
 */
@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

    /**
     * 基本异常
     */
    @ExceptionHandler(Exception.class)
    public R<Object> exception(Exception e) {
        log.error(e.getMessage(), e);
        return R.failed(new IErrorCode() {
            @Override
            public long getCode() {
                return 500;
            }

            @Override
            public String getMsg() {
                return "服务器执行失败";
            }
        });
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R<Object> exception(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return R.failed(new IErrorCode() {
            @Override
            public long getCode() {
                return 401;
            }

            @Override
            public String getMsg() {
                return "Unauthorized";
            }
        });
    }

    /**
     * 基本异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Object> exception(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        StringBuilder strBuilder = new StringBuilder();
        if (e.getBindingResult().hasErrors()) {
            List<FieldError> fieldErrors = Lists.newArrayList(e.getBindingResult().getFieldErrors());
            if (fieldErrors.size() > 1) {
                fieldErrors.sort(Comparator.comparing(FieldError::getField));
            }
            for (FieldError fieldError : fieldErrors) {
                strBuilder.append(fieldError.getDefaultMessage());
            }
        }
        return R.failed(new IErrorCode() {
            @Override
            public long getCode() {
                return 1002;
            }

            @Override
            public String getMsg() {
                return strBuilder.toString();
            }
        });
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R<Object> notFoundException() {
        return R.failed(new IErrorCode() {
            @Override
            public long getCode() {
                return 404;
            }

            @Override
            public String getMsg() {
                return "未定义的请求路径";
            }
        });
    }

    /**
     * 请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Object> httpRequestMethodNotSupportedException() {
        return R.failed(new IErrorCode() {
            @Override
            public long getCode() {
                return 405;
            }

            @Override
            public String getMsg() {
                return "Method not allowed";
            }
        });
    }

    /**
     * 请求参数异常
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, MissingServletRequestPartException.class, BindException.class})
    public R<Object> parameterException() {
        return R.failed(new IErrorCode() {
            @Override
            public long getCode() {
                return 403;
            }

            @Override
            public String getMsg() {
                return "Parameter error";
            }
        });
    }

    /**
     * 上传文件过大异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R<Object> maxUploadSizeExceededException() {
        return R.failed(new IErrorCode() {
            @Override
            public long getCode() {
                return 403;
            }

            @Override
            public String getMsg() {
                return "File is too large";
            }
        });
    }

    /**
     * 服务异常
     */
    @ExceptionHandler(BaseException.class)
    public R<Object> serviceException(BaseException e) {
        log.error(e.getMessage(), e);
        return R.failed(new IErrorCode() {
            @Override
            public long getCode() {
                return e.getCode();
            }

            @Override
            public String getMsg() {
                return e.getMessage();
            }
        });
    }

}