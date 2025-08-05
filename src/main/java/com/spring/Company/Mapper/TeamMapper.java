package com.spring.Company.Mapper;


import com.spring.Company.DTO.TeamDto;
import com.spring.Company.Model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {UserMapper.class})
public interface TeamMapper {
    @Mapping(source = "managerId", target = "manager")
    TeamDto teamToTeamDto(Team team);

    @Mapping(source = "manager", target = "managerId")
    Team teamDtoToTeam(TeamDto teamDto);
}
