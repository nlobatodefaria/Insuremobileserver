package pt.ulisboa.tecnico.sise.insure.ws.server.domain;

public class CustomerItem {

    private String _customerName;
    private int _fiscalNumber;
    private String _address;
    private String _dateOfBirth;
    private int _policyNumber;

    public CustomerItem(String customerName, int fiscalNumber, String address,
                        String dateOfBirth, int policyNumber) {

        _customerName = customerName;
        _fiscalNumber = fiscalNumber;
        _address = address;
        _dateOfBirth = dateOfBirth;
        _policyNumber = policyNumber;
    }

    public String getCustomerName() {
        return _customerName;
    }

    public int getFiscalNumber() {
        return _fiscalNumber;
    }

    public String getAddress() {
        return _address;
    }

    public String getDateOfBirth() {
        return _dateOfBirth;
    }

    public int getPolicyNumber() {
        return _policyNumber;
    }

    public String serialize() {
        return "Customer Name:" + _customerName + ", " +
                "Fiscal Number:" + _fiscalNumber + ", " +
                "Address:" + _address + ", " +
                "Date of Birth:" + _dateOfBirth + ", " +
                "Policy Number: " + _policyNumber + ".";
    }
}
