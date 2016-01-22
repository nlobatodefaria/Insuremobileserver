package pt.ulisboa.tecnico.sise.insure.ws.server.domain;

public class ClaimItem {

    private String _claimTitle;
    private int _claimId;

    public ClaimItem(String claimTitle, int claimId) {
        _claimTitle = claimTitle;
        _claimId = claimId;
    }

    public String getClaimTitle() {
        return _claimTitle;
    }

    public int getClaimId() {
        return _claimId;
    }

    public String serialize() {
        return "Claim Title: " + _claimTitle + ", " +
                "Claim Id: " + _claimId + ".";
    }
}
