package com.example.sendEmail.registration.token;

import com.example.sendEmail.exceptions.ServiceException;
import com.example.sendEmail.utils.codes.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void save(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }
    public Optional<ConfirmationToken> getToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }
    public ConfirmationToken update(ConfirmationToken confirmationToken) throws ServiceException {
        if (confirmationToken.getId() == null){
            throw ServiceException.builder()
                    .message("confirmationToken not found")
                    .code(ErrorCode.EMPTY_CODE)
                    .build();
        }
        return confirmationTokenRepository.save(confirmationToken);
    }
    public ConfirmationToken findById(Long id){
        return confirmationTokenRepository.findById(id).get();
    }
}
