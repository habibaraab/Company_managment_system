package com.spring.Company.Services;

import com.spring.Company.DTO.TeamDto;
import com.spring.Company.DTO.UserResponseDto;
import com.spring.Company.Mapper.TeamMapper;
import com.spring.Company.Mapper.UserMapper;
import com.spring.Company.Model.Team;
import com.spring.Company.Model.User;
import com.spring.Company.Repo.TeamRepository;
import com.spring.Company.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;
    private final UserMapper userMapper;


    public UserResponseDto getUserById(int id) {
        log.info("Attempting to find user by ID: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with ID: {}. Throwing EntityNotFoundException.", id);
            return new EntityNotFoundException("user not found" + id);
        });
        log.info("Successfully found user with ID: {}", id);
        return userMapper.toUserResponseDto(user);
    }

    public TeamDto createTeam(TeamDto teamDto) {
        log.info("Attempting to create a new team with name: {}", teamDto.getName());
        log.debug("Mapping TeamDto to Team entity.");
        Team team = teamMapper.teamDtoToTeam(teamDto);
        log.debug("Saving new team to the database.");
        Team savedTeam = teamRepository.save(team);
        log.info("Successfully created team '{}' with ID: {}", savedTeam.getName(), savedTeam.getId());
        return teamMapper.teamToTeamDto(savedTeam);
    }

    public void addMemberToTeam(int teamId, int userId) {
        log.info("Attempting to add member with ID {} to team with ID {}", userId, teamId);
        Team team = teamRepository.findById(teamId).orElseThrow(() -> {
            log.error("Add member failed: Team with ID {} not found.", teamId);
            return new EntityNotFoundException("team not found");
        });
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("Add member failed: User with ID {} not found.", userId);
            return new EntityNotFoundException("user not found");
        });
        log.debug("Adding user '{}' to members of team '{}'.", user.getName(), team.getName());
        team.getMembers().add(user);
        teamRepository.save(team);
        log.info("Successfully added member with ID {} to team with ID {}", userId, teamId);
    }

    public TeamDto assignManagerToTeam(int teamId, int managerId) {
        log.info("Attempting to assign manager with ID {} to team with ID {}", managerId, teamId);
        User manager = userRepository.findById(managerId).orElseThrow(() -> {
            log.error("Assign manager failed: Manager with ID {} not found.", managerId);
            return new EntityNotFoundException("user not found");
        });
        Team team = teamRepository.findById(teamId).orElseThrow(() -> {
            log.error("Assign manager failed: Team with ID {} not found.", teamId);
            return new EntityNotFoundException("team not found");
        });
        log.debug("Setting manager '{}' for team '{}'.", manager.getName(), team.getName());
        team.setManagerId(manager);
        Team savedTeam = teamRepository.save(team);
        log.info("Successfully assigned manager with ID {} to team with ID {}", managerId, teamId);
        return teamMapper.teamToTeamDto(savedTeam);
    }

    public TeamDto getTeamById(int teamId) {
        log.info("Attempting to find team by ID: {}", teamId);
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isPresent()) {
            log.info("Successfully found team with ID: {}", teamId);
            return teamMapper.teamToTeamDto(teamOptional.get());
        } else {
            log.error("Team not found with ID: {}. Throwing EntityNotFoundException.", teamId);
            throw new EntityNotFoundException("team id not found:" + teamId);
        }
    }

    public List<TeamDto> getAllTeams() {
        log.info("Attempting to fetch all teams.");
        List<Team> teams = teamRepository.findAll();
        log.info("Found {} teams.", teams.size());
        return teams.stream()
                .map(teamMapper::teamToTeamDto)
                .collect(Collectors.toList());
    }

    public void removeMemberFromTeam(int teamId, int userId) {
        log.info("Attempting to remove member with ID {} from team with ID {}", userId, teamId);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("Remove member failed: User with ID {} not found.", userId);
            return new EntityNotFoundException("user not found");
        });
        Team team = teamRepository.findById(teamId).orElseThrow(() -> {
            log.error("Remove member failed: Team with ID {} not found.", teamId);
            return new EntityNotFoundException("team not found");
        });

        if (team.getMembers().contains(user)) {
            log.debug("Removing user '{}' from members of team '{}'.", user.getName(), team.getName());
            team.getMembers().remove(user);
            teamRepository.save(team);
            log.info("Successfully removed member with ID {} from team with ID {}", userId, teamId);
        } else {
            log.warn("Remove member action skipped: User with ID {} was not a member of team with ID {}.", userId, teamId);
        }
    }

    public List<TeamDto> getTeamsByManagerId(int managerId) {
        log.info("Attempting to find teams managed by manager with ID: {}", managerId);
        if (!userRepository.existsById(managerId)) {
            log.error("Could not find teams: Manager with ID {} does not exist.", managerId);
            throw new EntityNotFoundException("this manager not found " + managerId);
        }
        List<Team> teams = teamRepository.findByManagerId(managerId);
        log.info("Found {} teams for manager with ID: {}", teams.size(), managerId);
        return teams.stream()
                .map(teamMapper::teamToTeamDto)
                .collect(Collectors.toList());
    }

    public TeamDto getTeamByMemberId(int memberId) {
        log.info("Attempting to find team by member ID: {}", memberId);
        Optional<Team> teamOptional = teamRepository.findFirstByMembers_Id(memberId);
        if (teamOptional.isPresent()) {
            log.info("Successfully found team for member with ID: {}", memberId);
            return teamMapper.teamToTeamDto(teamOptional.get());
        } else {
            log.error("No team found for member with ID: {}. Throwing EntityNotFoundException.", memberId);
            throw new EntityNotFoundException("No team found for member with id: " + memberId);
        }
    }
}