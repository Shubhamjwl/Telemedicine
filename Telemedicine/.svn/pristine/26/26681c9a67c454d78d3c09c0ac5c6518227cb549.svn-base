package com.nsdl.ndhm.transfer.entity;

import lombok.*;
import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
/*@Table(name = "hiprequest", schema = "ndhm")*/
@Table(name = "hiprequest")
public class HipRequestEntity {

    @Id
    private String requestId;
    private String timestamp;
    private String transactionId;
    private String consentId;
    private String dateRangeFrom;
    private String dateRangeTo;
    private String dataPushUrl;
    private String cryptoAlg;
    private String curve;
    private String dhPublicKeyExpiry;
    private String dhPublicKeyParameters;
    private String keyValue;
    private String nonce;
}
