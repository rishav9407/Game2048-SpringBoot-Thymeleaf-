package com.exponent.energy._game.controller;

import com.exponent.energy._game.model.Board;
import com.exponent.energy._game.service.GameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class GameController {

    private final GameService gameService;
    public static final String SESSION_KEY = "board";

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Board board = getOrCreateBoard(session);
        model.addAttribute("boardSize", board.getSize());
        model.addAttribute("score", board.getScore());
        // initial page will fetch full state by JS via /state
        return "index";
    }

    @GetMapping("/state")
    @ResponseBody
    public Object getState(HttpSession session) {
        Board board = getOrCreateBoard(session);
        return toDto(board);
    }

    @PostMapping("/move")
    @ResponseBody
    public Object move(@RequestParam String dir, HttpSession session) {
        Board board = getOrCreateBoard(session);
        boolean changed = gameService.applyMove(board, dir);
        // if changed, board state is already mutated and spawn done inside Board
        session.setAttribute(SESSION_KEY, board);
        return toDto(board);
    }

    @PostMapping("/restart")
    @ResponseBody
    public Object restart(HttpSession session) {
        Board newBoard = gameService.createNew();
        session.setAttribute(SESSION_KEY, newBoard);
        return toDto(newBoard);
    }

    private Board getOrCreateBoard(HttpSession session) {
        Board board = (Board) session.getAttribute(SESSION_KEY);
        if (board == null) {
            board = gameService.createNew();
            session.setAttribute(SESSION_KEY, board);
        }
        return board;
    }

    private Object toDto(Board b) {
        return java.util.Map.of(
                "size", b.getSize(),
                "score", b.getScore(),
                "won", b.isWon(),
                "gameOver", b.isGameOver(),
                "grid", IntStream.range(0, b.getSize())
                        .mapToObj(r -> IntStream.range(0, b.getSize())
                                .mapToObj(c -> {
                                    Integer v = b.getGrid()[r][c].getValue();
                                    return v == null ? 0 : v;
                                }).collect(Collectors.toList()))
                        .collect(Collectors.toList())
        );
    }
}