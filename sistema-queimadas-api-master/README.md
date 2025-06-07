# FireGuard API

## Descrição

API para prevenção e combate a queimadas, monitorando áreas, sensores, leituras ambientais e gerando alertas automáticos. Permite cadastro e autenticação de usuários com diferentes perfis (roles), integração de sensores e acompanhamento de alertas em tempo real.

## Tecnologias
- Java 21
- Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA (H2 Database por padrão)
- Maven
- Swagger (OpenAPI)

## Como Executar

1. **Pré-requisitos:**
   - JDK 21
   - Maven

2. **Clone o repositório e acesse a pasta:**
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd sistema-queimadas-api-master
   ```

3. **Build e execução:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   Acesse: http://localhost:8080

4. **Banco de dados:**
   - H2 em memória (console: http://localhost:8080/h2-console)
   - JDBC URL: `jdbc:h2:mem:fireguard_db` | User: `sa` | Password: (vazio)

5. **Swagger:**
   - Documentação interativa: http://localhost:8080/swagger-ui.html ou http://localhost:8080/swagger-ui/
   - OpenAPI JSON: http://localhost:8080/v3/api-docs

## Perfis de Usuário (roles)
- BOMBEIRO
- DEFESA_CIVIL
- GESTOR_AMBIENTAL
- FAZENDEIRO
- ADMIN

## Sequência Recomendada de Uso da API

1. **Cadastro e Autenticação**
   - `POST /api/auth/register` — Cadastrar novo usuário (envie nome, email, senha, telefone, tipoPerfil)
   - `POST /api/auth/login` — Autenticar usuário (envie email/nome e senha) → retorna token JWT

2. **Gerenciamento de Áreas de Monitoramento**
   - `POST /api/areas` — Cadastrar nova área
   - `GET /api/areas` — Listar áreas
   - `GET /api/areas/{id}` — Buscar área por ID
   - `PUT /api/areas/{id}` — Atualizar área
   - `DELETE /api/areas/{id}` — Remover área

3. **Gerenciamento de Sensores**
   - `POST /api/sensores` — Cadastrar sensor (vincule a uma área existente)
   - `GET /api/sensores` — Listar sensores
   - `GET /api/sensores/{id}` — Buscar sensor por ID
   - `PUT /api/sensores/{id}` — Atualizar sensor
   - `DELETE /api/sensores/{id}` — Remover sensor
   - `GET /api/sensores/area/{areaId}` — Listar sensores de uma área

4. **Leituras de Sensores**
   - `POST /api/leituras` — Registrar leitura (sensor, temperatura, umidade, fumaça, timestamp)
   - `GET /api/leituras` — Listar todas as leituras
   - `GET /api/leituras/{id}` — Buscar leitura por ID
   - `GET /api/leituras/sensor/{sensorId}` — Listar leituras de um sensor

5. **Alertas**
   - `GET /api/alertas` — Listar alertas
   - `GET /api/alertas/{id}` — Buscar alerta por ID
   - `GET /api/alertas/status/{status}` — Listar alertas por status
   - `PUT /api/alertas/{id}/status` — Atualizar status do alerta

6. **Gerenciamento de Usuários (ADMIN)**
   - `GET /api/usuarios` — Listar usuários
   - `GET /api/usuarios/{id}` — Buscar usuário por ID
   - `PUT /api/usuarios/{id}` — Atualizar usuário
   - `DELETE /api/usuarios/{id}` — Remover usuário

## Observações
- Sempre envie o token JWT no header `Authorization: Bearer <token>` para endpoints protegidos.
- O sistema gera alertas automaticamente com base nas leituras dos sensores.
- Perfis de usuário controlam o acesso a determinadas operações (ex: apenas ADMIN pode gerenciar usuários).
- Consulte o Swagger para exemplos de payloads e respostas.

---

Para dúvidas, consulte a documentação Swagger ou abra uma issue.
