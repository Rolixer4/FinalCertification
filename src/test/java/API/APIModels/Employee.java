package API.APIModels;

public record Employee(String firstName, String lastName, String middleName, int companyId, String email, String phone, boolean isActive) {
}
