package models;

/**
 * User bean for testing
 * Created by qiuzhexin on 11/20/15.
 */
public class UserBean {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String middleInitial;
    private String affiliation;
    private String title;
    private String email;
    private String mailingAddress;
    private String phoneNumber;
    private String faxNumber;
    private String researchFields;
    private String highestDegree;

    public UserBean() {
    }

    public UserBean(String userName, String password, String firstName, String lastName, String middleInitial, String affiliation, String title, String email, String mailingAddress, String phoneNumber, String faxNumber, String researchFields, String highestDegree) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInitial = middleInitial;
        this.affiliation = affiliation;
        this.title = title;
        this.email = email;
        this.mailingAddress = mailingAddress;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.researchFields = researchFields;
        this.highestDegree = highestDegree;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getTitle() {
        return title;
    }

    public String getEmail() {
        return email;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public String getResearchFields() {
        return researchFields;
    }

    public String getHighestDegree() {
        return highestDegree;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public void setResearchFields(String researchFields) {
        this.researchFields = researchFields;
    }

    public void setHighestDegree(String highestDegree) {
        this.highestDegree = highestDegree;
    }
}
