package ru.cft.croudfounding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.croudfounding.model.DonateRequest;
import ru.cft.croudfounding.repository.DonationRepository;
import ru.cft.croudfounding.repository.model.Donation;
import ru.cft.croudfounding.repository.model.Project;
import ru.cft.croudfounding.repository.model.User;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    public void saveDonation(User user, Project project, DonateRequest donateRequest) {
        Donation donation = new Donation();
        donation.setIdUser(user);
        donation.setIdProject(project);
        donation.setAmount(donateRequest.getDonationAmount());
        donationRepository.save(donation);
    }
}
