package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.AccountRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.AdminStatus;

@Mapper(componentModel = "spring")
public interface AccountRequestMapper {

    @Mapping(source = "user.id", target = "user_Id")
    @Mapping(source = "id", target = "requestId")
    AdminStatus toAdminStatus(AccountRequestEntity accountRequestEntity);
}
