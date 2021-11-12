package com.gestionnaire_de_stage.controller;

import com.gestionnaire_de_stage.dto.EvalMilieuStageDTO;
import com.gestionnaire_de_stage.model.Contract;
import com.gestionnaire_de_stage.model.Stage;
import com.gestionnaire_de_stage.service.StageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stages")
@CrossOrigin
public class StageController {

    private final StageService stageService;

    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

/*    @PostMapping("/supervisor/fill_form")
    public ResponseEntity<?> fillEvalMilieuStage(@RequestBody EvalMilieuStageDTO evalMilieuStageDTO) {
        Stage stage = new Stage();
        Contract contract =
        stage.setContract();

        try {

        }
    }*/
}
