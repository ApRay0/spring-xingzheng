package anjtech.demo.service;

import anjtech.demo.po.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TeamService {
    Team saveTeam(Team team);

    void deleteTeam(Long id);

    Page<Team> listAll(Pageable pageable);

    List<Team> listAll();

    Team findByName(String name);

    Team findById(Long id);
}
