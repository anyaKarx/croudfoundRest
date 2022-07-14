package ru.cft.croudfounding.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class DonateRequest {

    @JsonProperty("donation_amount")
    @NotNull
    private Long donationAmount;
}
