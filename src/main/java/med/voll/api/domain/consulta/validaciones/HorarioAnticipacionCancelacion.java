package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelacionConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioAnticipacionCancelacion implements ValidacionesCancelacion {

    @Autowired
    ConsultaRepository consultaRepository;

    @Override
    public void validar(DatosCancelacionConsulta datos) {
        var consulta = consultaRepository.getReferenceById(datos.id());
        var ahora = LocalDateTime.now();
        var horaDeConsulta = consulta.getFecha();

        var diferenciaDe24hs = Duration.between(ahora, horaDeConsulta).toHours() < 24;

        if (diferenciaDe24hs) {
            throw new ValidationException("las consultas deben cancelarse con al menos 24 horas de anticipación");
        }
    }
}
