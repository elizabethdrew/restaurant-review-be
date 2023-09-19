package dev.drew.restaurantreview.mapper;

import dev.drew.restaurantreview.entity.ClaimEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.ClaimInput;
import org.openapitools.model.ClaimStatus;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    @Mapping(source = "claimant.id", target = "claimant")
    ClaimStatus toClaim(ClaimEntity claimEntity);

    ClaimEntity toClaimEntity(ClaimInput claimInput);
}
