package gg.cse.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SummonerRepository extends JpaRepository<Summoner, String> {
    Optional<Summoner> findBySummonerId(String summonerId);
    Optional<Summoner> findByNameIgnoreCase(String name);
}
