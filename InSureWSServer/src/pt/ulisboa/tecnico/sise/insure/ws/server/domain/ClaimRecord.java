package pt.ulisboa.tecnico.sise.insure.ws.server.domain;

public class ClaimRecord {

    private int _claimId;
    private String _submissionDate;
    private String _claimTitle;
    private String _description;
    private String _status;
    private boolean _authorized;

    public ClaimRecord(int claimId, String submissionDate, String claimTitle,
                       String description, String status, boolean authorized) {
        _claimId = claimId;
        _submissionDate = submissionDate;
        _claimTitle = claimTitle;
        _description = description;
        _status = status;
        _authorized = authorized;
    }

    public int getClaimId() {
        return _claimId;
    }

    public String getSubmissionDate() {
        return _submissionDate;
    }

    public String getClaimTitle() {
        return _claimTitle;
    }

    public String getDescription() {
        return _description;
    }

    public String getStatus() {
        return _status;
    }
    
    public boolean getAuthorized() {
    	return _authorized;
    }

    public void setAuthorized(boolean decision) {
        _authorized = decision;
    }
    
    public String serialize() {
        return "Claim Id: " + _claimId + ", " +
                "Submission Date: " + _submissionDate + ", " +
                "Claim Title: " + _claimTitle + ", " +
                "Description: " + _description + ", " +
                "Status: " + _status + ", " +
                "Authorized:" + _authorized + ".";
    }
}
