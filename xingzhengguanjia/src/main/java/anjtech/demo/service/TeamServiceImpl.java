package anjtech.demo.service;

import anjtech.demo.dao.TeamRepository;
import anjtech.demo.po.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    @Override
    public Team saveTeam(Team group) {
        return teamRepository.save(group);
    }

    @Transactional
    @Override
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public Page<Team> listAll(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public List<Team> listAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team findById(Long id) {
        return teamRepository.getOne(id);
    }
}
