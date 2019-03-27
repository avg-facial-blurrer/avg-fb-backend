package nl.oose.blackpool.DTO;

public class VerifiedTokenResponseDTO {
    private boolean isVerified;

    public VerifiedTokenResponseDTO() {

    }

    public VerifiedTokenResponseDTO(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}