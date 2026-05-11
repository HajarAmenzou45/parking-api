package com.smartparking.parking_api.service;

import com.smartparking.parking_api.dto.HistoryDTO;
import com.smartparking.parking_api.dto.LoginRequest;
import com.smartparking.parking_api.dto.RegisterRequest;
import com.smartparking.parking_api.dto.UpdateProfileRequest;
import com.smartparking.parking_api.dto.UserStatsDTO;

import com.smartparking.parking_api.entity.Paiement;
import com.smartparking.parking_api.entity.utilisateur;

import com.smartparking.parking_api.enums.RoleUtilisateur;

import com.smartparking.parking_api.repository.PaiementRepository;
import com.smartparking.parking_api.repository.ReservationRepository;
import com.smartparking.parking_api.repository.VehiculeRepository;
import com.smartparking.parking_api.repository.utilisateurRepository;

import com.smartparking.parking_api.security.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class utilisateurService {

    private final utilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final PaiementRepository paiementRepository;
    private final ReservationRepository reservationRepository;
    private final VehiculeRepository vehiculeRepository;

    public utilisateurService(
            utilisateurRepository repository,
            PasswordEncoder passwordEncoder,
            PaiementRepository paiementRepository,
            ReservationRepository reservationRepository,
            VehiculeRepository vehiculeRepository
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.paiementRepository = paiementRepository;
        this.reservationRepository = reservationRepository;
        this.vehiculeRepository = vehiculeRepository;
    }

    // REGISTER
    public utilisateur register(RegisterRequest request){

        if(repository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email déjà utilisé");
        }

        utilisateur u = new utilisateur();

        u.setEmail(request.getEmail());
        u.setNomComplet(request.getNomComplet());

        u.setMotDePasse(
                passwordEncoder.encode(
                        request.getMotDePasse()
                )
        );

        u.setRole(RoleUtilisateur.CLIENT);

        return repository.save(u);
    }

    // LOGIN
    public String login(LoginRequest request){

        utilisateur user = repository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if(!passwordEncoder.matches(
                request.getMotDePasse(),
                user.getMotDePasse()
        )){
            throw new RuntimeException("Password incorrect");
        }

        return JwtUtil.generateToken(user.getEmail());
    }

    // GET ALL USERS
    public List<utilisateur> getAll(){

        return repository.findAll();
    }

    // GET USER BY EMAIL
    public utilisateur getByEmail(String email){

        return repository.findByEmail(email)
                .orElse(null);
    }

    // CURRENT USER
    public utilisateur getCurrentUser(String email){

        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    // UPDATE PROFILE
    public utilisateur updateProfile(
            String email,
            UpdateProfileRequest request
    ){

        utilisateur user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setNomComplet(request.nomComplet);
        user.setUsername(request.username);
        user.setTelephone(request.telephone);
        user.setGenre(request.genre);
        user.setCarteBancaire(request.carteBancaire);

        return repository.save(user);
    }

    // USER HISTORY
    public List<HistoryDTO> getHistory(String email){

        utilisateur user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Paiement> paiements =
                paiementRepository.findUserHistory(user);

        List<HistoryDTO> history = new ArrayList<>();

        for(Paiement p : paiements){

            HistoryDTO dto = new HistoryDTO();

            dto.parking = p.getTicket()
                    .getPlace()
                    .getParking()
                    .getNom();

            dto.date = p.getTicket()
                    .getHeureEntree()
                    .toLocalDate()
                    .toString();

            dto.duree = p.getTicket().getDuree();

            dto.montant = p.getMontant();

            dto.statutPaiement =
                    p.getStatut().name();

            history.add(dto);
        }

        return history;
    }

    // USER STATS
    public UserStatsDTO getStats(String email){

        utilisateur user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        UserStatsDTO dto = new UserStatsDTO();

        dto.reservations =
                reservationRepository.countByUtilisateur(user);

        dto.vehicules =
                vehiculeRepository.findByUtilisateur(user).size();

        dto.paiements =
                paiementRepository.findUserHistory(user).size();

        return dto;
    }
}