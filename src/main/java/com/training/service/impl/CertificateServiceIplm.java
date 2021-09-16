package com.training.service.impl;

import com.training.entities.Certificate;
import com.training.repository.CertificateRepository;
import com.training.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceIplm implements CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Override
    public void createCertificate(Certificate certificate) {
        certificateRepository.save(certificate);

    }
}
