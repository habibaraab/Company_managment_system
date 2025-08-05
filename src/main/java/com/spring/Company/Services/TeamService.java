package com.spring.Company.Services;


import com.spring.Company.DTO.TeamDto;
import com.spring.Company.DTO.UserResponseDto;
import com.spring.Company.Mapper.TeamMapper;
import com.spring.Company.Mapper.UserMapper;
import com.spring.Company.Mapper.UserProjection;
import com.spring.Company.Model.Team;
import com.spring.Company.Model.User;
import com.spring.Company.Repo.TeamRepository;
import com.spring.Company.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserMapper userMapper;


    public UserResponseDto getUserById(int id) {
        User user=userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("user not found"+id));
        return userMapper.toUserResponseDto(user);
    }

    public TeamDto createTeam(TeamDto teamDto) {
        Team team = teamMapper.teamDtoToTeam(teamDto);
        Team savedTeam = teamRepository.save(team);
        return teamMapper.teamToTeamDto(savedTeam);
    }

    public void addMemberToTeam(int teamId, int userId) {
        Team team=teamRepository.findById(teamId).orElseThrow(()->new EntityNotFoundException("team not found"));
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("user not found"));

        team.getMembers().add(user);
        teamRepository.save(team);
    }

    public TeamDto assignManagerToTeam(int teamId, int managerId) {
        User manager=userRepository.findById(managerId).orElseThrow(()->new EntityNotFoundException("user not found"));
        Team team=teamRepository.findById(teamId).orElseThrow(()->new EntityNotFoundException("team not found"));
        team.setManagerId(manager);
        return teamMapper.teamToTeamDto(team);
    }

    public TeamDto getTeamById(int teamId) {
        return teamRepository.findById(teamId)
                .map(teamMapper::teamToTeamDto)
                .orElseThrow(() -> new EntityNotFoundException("team id not found:" + teamId));
    }

    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(teamMapper::teamToTeamDto)
                .collect(Collectors.toList());
    }

    public void removeMemberFromTeam(int teamId, int userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("user not found"));
       Team team= teamRepository.findById(teamId).orElseThrow(()->new EntityNotFoundException("team not found"));
        team.getMembers().remove(user);
        teamRepository.save(team);
    }

    public List<TeamDto> getTeamsByManagerId(int managerId) {
        if (!userRepository.existsById(managerId)) {
            throw new EntityNotFoundException("this manager not found " + managerId);
        }
        return teamRepository.findByManagerId(managerId)
                .stream()
                .map(teamMapper::teamToTeamDto)
                .collect(Collectors.toList());
    }


    public TeamDto getTeamByMemberId(int memberId) {
        return teamRepository.findFirstByMembers_Id(memberId)
                .map(teamMapper::teamToTeamDto)
                .orElseThrow(() -> new EntityNotFoundException("No team found for member with id: " + memberId));
    }
}
