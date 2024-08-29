package com.codigo.ms_seguridad.aggregates.constants;

public class Constants {
    public static final Boolean ESTADO_ACTIVO=true;
    public static final String CLAIM_ROLE = "rol";
    public static final String ENPOINTS_PERMIT = "/api/authentication/v1/**";
    public static final String ENPOINTS_PERMIT_ACTUATOR = "/actuator/refresh";
    public static final String ENPOINTS_USER = "/api/user/v1/**";
    public static final String ENPOINTS_ADMIN = "/api/admin/v1/**";
    public static final String CLAVE_AccountNonExpired ="isAccountNonExpired";
    public static final String CLAVE_AccountNonLocked ="isAccountNonLocked";
    public static final String CLAVE_CredentialsNonExpired = "isCredentialsNonExpired";
    public static final String CLAVE_Enabled = "isEnabled";

}
