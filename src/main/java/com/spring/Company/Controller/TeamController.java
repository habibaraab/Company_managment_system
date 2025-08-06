package com.spring.Company.Controller;

import com.spring.Company.DTO.TeamDto;
import com.spring.Company.DTO.UserResponseDto;
import com.spring.Company.Services.ManagerService;
import com.spring.Company.Services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private  final  TeamService teamService;
    private final  ManagerService managerService   ;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable int id) {
        UserResponseDto user=managerService.findUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/createTeam")
    public ResponseEntity<TeamDto>createTeam(@RequestBody TeamDto teamDto) {
        TeamDto team = teamService.createTeam(teamDto);
        return new ResponseEntity<>(team, HttpStatus.CREATED);
    }

    @GetMapping("/getTeamById/{id}")
    public ResponseEntity<TeamDto>getTeamById(@PathVariable int id) {
        TeamDto team = teamService.getTeamById(id);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @GetMapping("/getAllTeams")
    public ResponseEntity<List<TeamDto>>getAllTeams() {
        List<TeamDto> teams = teamService.getAllTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @PutMapping("/{teamId}/manager/{managerId}")
    public ResponseEntity<TeamDto>assignManager(@PathVariable int teamId, @PathVariable int managerId) {
        TeamDto team = teamService.assignManagerToTeam(teamId, managerId);
        return new ResponseEntity<>(team, HttpStatus.OK);
        }

        @PostMapping("/{teamId}/member/{memberId}")
    public ResponseEntity<Void>assignMember(@PathVariable int teamId, @PathVariable int memberId) {
            teamService.addMemberToTeam(teamId, memberId);
            return ResponseEntity.ok().build();
        }

    @DeleteMapping("/{teamId}/members/{userId}")
    public ResponseEntity<Void>removeMember(@PathVariable int teamId,@PathVariable int userId){
        teamService.removeMemberFromTeam(teamId,userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-manager/{managerId}")
    public ResponseEntity<List<TeamDto>>getTeamByManagerId(@PathVariable int managerId ){
        List<TeamDto> teamDto=teamService.getTeamsByManagerId(managerId);
        return  new ResponseEntity<>(teamDto,HttpStatus.OK);
    }

    @GetMapping("/by-member/{memberId}")
    public ResponseEntity<TeamDto> getTeamByMemberId(@PathVariable int memberId) {
        return ResponseEntity.ok(teamService.getTeamByMemberId(memberId));
    }
}
