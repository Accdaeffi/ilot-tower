package ru.ilot.ilottower.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilot.ilottower.model.entities.user.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
