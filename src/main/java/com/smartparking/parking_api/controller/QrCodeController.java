package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Ticket;
import com.smartparking.parking_api.repository.TicketRepository;
import com.smartparking.parking_api.service.QrCodeService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/qr")
public class QrCodeController {

    private final QrCodeService qrCodeService;
    private final TicketRepository ticketRepo;

    public QrCodeController(
            QrCodeService qrCodeService,
            TicketRepository ticketRepo
    ) {
        this.qrCodeService = qrCodeService;
        this.ticketRepo = ticketRepo;
    }

    @GetMapping(
            value = "/ticket/{ticketId}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] getQrCode(
            @PathVariable Integer ticketId
    ) throws Exception {

        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        String qrText =
                "Ticket ID: " + ticket.getId()
                        + "\nParking: "
                        + ticket.getPlace()
                        .getParking()
                        .getNom()
                        + "\nPlace: "
                        + ticket.getPlace()
                        .getNumero()
                        + "\nMontant: "
                        + ticket.getMontant();

        BufferedImage image =
                qrCodeService.generateQrCode(qrText);

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        ImageIO.write(image, "PNG", output);

        return output.toByteArray();
    }
}