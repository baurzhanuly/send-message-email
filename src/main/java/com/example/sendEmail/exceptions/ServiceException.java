package com.example.sendEmail.exceptions;

import com.example.sendEmail.utils.codes.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceException extends Exception{

    protected String message;
    protected ErrorCode code;

}
