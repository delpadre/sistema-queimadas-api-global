package com.sistemadequeimadas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sistemadequeimadas.enums.TipoAlerta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_alerta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alerta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Área de monitoramento não pode ser nula")
	@ManyToOne
	@JoinColumn(name = "area_monitoramento_id", nullable = false)
	@JsonIgnore
	private AreaMonitoramento areaMonitoramento;

	@NotNull(message = "Leitura do sensor que gerou o alerta não pode ser nula")
	@OneToOne
	@JoinColumn(name = "leitura_sensor_id", unique = true, nullable = false)
	private LeituraSensor leituraSensor;

	@NotNull(message = "Tipo de alerta não pode ser nulo")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoAlerta tipoAlerta;

	@NotBlank(message = "Mensagem do alerta não pode estar em branco")
	@Column(columnDefinition = "TEXT")
	private String mensagem;

	@NotNull(message = "Timestamp do alerta não pode ser nulo")
	@Column(nullable = false)
	private LocalDateTime timestampAlerta;

	@NotBlank(message = "Status do alerta não pode estar em branco")
	@Column(nullable = false)
	private String status; // Ex: "ATIVO", "RESOLVIDO", "FALSO_POSITIVO"

	public Alerta(AreaMonitoramento areaMonitoramento, LeituraSensor leituraSensor, TipoAlerta tipoAlerta,
			String mensagem) {
		this.areaMonitoramento = areaMonitoramento;
		this.leituraSensor = leituraSensor;
		this.tipoAlerta = tipoAlerta;
		this.mensagem = mensagem;
		this.timestampAlerta = LocalDateTime.now();
		this.status = "ATIVO";
	}
}
