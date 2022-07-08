package ru.cft.croudfounding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.croudfounding.repository.model.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> {

}
