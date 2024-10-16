//ESTE DIAGRAMA FOI CRIADO NO dbdiagram.io

Table users {
  id INT [primary key]
  num_mecanografico INTEGER
  password VARCHAR
  role ENUM('Atleta', 'Gestor')
  created_at TIMESTAMP
}

Table athletes {
  id INT [primary key]
  nome VARCHAR
  data_nascimento DATE
  genero ENUM('Masculino', 'Feminino')
  altura_cm INT
  peso_kg DECIMAL
  pais VARCHAR
  user_id INT
}

Table manager {
  id INT [primary key]
  nome VARCHAR
  user_id INT
}

Table sports {
  id INT [primary key]
  nome VARCHAR
  tipo ENUM('Individual', 'Coletivo')
  descricao TEXT
  min_participantes INT
  pontuacao ENUM('Tempo', 'Pontos', 'Distância')
  one_game BOOLEAN
  recorde_olimpico DECIMAL
  vencedor_olimpico VARCHAR
  regras TEXT
  venue_id INT  /* Local onde a modalidade ocorre */
}

Table teams {
  id INT [primary key]
  pais VARCHAR
  ano_fundacao YEAR
  participacoes INT
  medalhas INT
}

Table venues {
  id INT [primary key]
  nome VARCHAR
  tipo ENUM('Interior', 'Exterior')
  morada VARCHAR
  cidade VARCHAR
  capacidade INT
  ano_construcao YEAR
}

Table events {
  id INT [primary key]
  ano_edicao YEAR
  pais_anfitriao VARCHAR
  logo VARCHAR
  mascote VARCHAR
  venue_id INT  /* Local onde o evento ocorre */
}

Table results {
  id INT [primary key]
  data DATE
  resultado DECIMAL
  tipo_resultado ENUM('Tempo', 'Pontos', 'Distância')
  medalha ENUM('Ouro', 'Prata', 'Bronze', 'Nenhuma')
  modalidade_id INT
  atleta_id INT
  equipa_id INT
  venue_id INT  /* Local onde ocorreu o resultado */
}

Table registrations {
  id INT [primary key]
  status ENUM('Pendente', 'Aprovado', 'Rejeitado')
  modalidade_id INT
  atleta_id INT
  equipa_id INT
}

Table athlete_sports {
  atleta_id INT
  modalidade_id INT
}

Table athlete_teams {
  atleta_id INT
  equipa_id INT
}

/* Definição das chaves estrangeiras e relações */

/* Associação entre um atleta e um utilizador (User-Atleta) */
Ref: athletes.user_id > users.id

/* Associação entre um gestor e um utilizador (User-Gestor) */
Ref: manager.user_id > users.id

/* Associação entre modalidades (sports) e locais (venues) */
Ref: sports.venue_id > venues.id

/* Associação entre eventos (events) e locais (venues) */
Ref: events.venue_id > venues.id

/* Associação entre resultados (results) e locais (venues), modalidades, atletas ou equipas */
Ref: results.modalidade_id > sports.id
Ref: results.atleta_id > athletes.id
Ref: results.equipa_id > teams.id
Ref: results.venue_id > venues.id

/* Associação entre inscrições (registrations) e modalidades, atletas ou equipas */
Ref: registrations.modalidade_id > sports.id
Ref: registrations.atleta_id > athletes.id
Ref: registrations.equipa_id > teams.id

/* Associação entre atleta e modalidades (Atleta pode participar em várias modalidades) */
Ref: athlete_sports.atleta_id > athletes.id
Ref: athlete_sports.modalidade_id > sports.id

/* Associação entre atleta e equipa (Atleta pode pertencer a uma equipa) */
Ref: athlete_teams.atleta_id > athletes.id
Ref: athlete_teams.equipa_id > teams.id
