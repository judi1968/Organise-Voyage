
CREATE SEQUENCE activite_id_activite_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE bouquet_id_bouquet_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE durre_id_duree_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE lieu_id_lieu_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE type_lieu_id_type_lieu_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE type_lieu_nom_type_lieu_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE voyage_id_voyage_seq START WITH 1 INCREMENT BY 1;

CREATE  TABLE activite ( 
	id_activite          integer DEFAULT nextval('activite_id_activite_seq'::regclass) NOT NULL  ,
	nom_activite         varchar(255)    ,
	CONSTRAINT pk_activite PRIMARY KEY ( id_activite )
 );

CREATE  TABLE activite_prix ( 
	id_activite_fk       integer    ,
	prix                 double precision    ,
	etat                 integer    ,
	CONSTRAINT fk_activite_prix_activite FOREIGN KEY ( id_activite_fk ) REFERENCES activite( id_activite )   
 );

CREATE  TABLE activite_voyage ( 
	id_voyage_fk         integer    ,
	id_activite_fk       integer    ,
	id_durre_fk          integer    
 );

CREATE  TABLE billet_activite_entrant ( 
	id_activite_fk       integer  NOT NULL  ,
	quantite             integer    ,
	CONSTRAINT fk_billet_activite_entrant FOREIGN KEY ( id_activite_fk ) REFERENCES activite( id_activite )   
 );

CREATE  TABLE bouquet ( 
	id_bouquet           integer DEFAULT nextval('bouquet_id_bouquet_seq'::regclass) NOT NULL  ,
	nom_bouquet          varchar(255)    ,
	CONSTRAINT pk_bouquet PRIMARY KEY ( id_bouquet )
 );

CREATE  TABLE durre ( 
	id_duree             integer DEFAULT nextval('durre_id_duree_seq'::regclass) NOT NULL  ,
	nom_duree            varchar    ,
	CONSTRAINT pk_durre PRIMARY KEY ( id_duree )
 );

CREATE  TABLE type_lieu ( 
	id_type_lieu         integer DEFAULT nextval('type_lieu_id_type_lieu_seq'::regclass) NOT NULL  ,
	nom_type_lieu        varchar DEFAULT nextval('type_lieu_nom_type_lieu_seq'::regclass) NOT NULL  ,
	CONSTRAINT pk_type_lieu PRIMARY KEY ( id_type_lieu )
 );

CREATE  TABLE activite_bouquet ( 
	id_activite_fk       integer    ,
	id_bouquet_fk        integer    ,
	CONSTRAINT fk_activite_bouquet_activite FOREIGN KEY ( id_activite_fk ) REFERENCES activite( id_activite )   ,
	CONSTRAINT fk_bouquet_activite_bouquet FOREIGN KEY ( id_bouquet_fk ) REFERENCES bouquet( id_bouquet )   
 );

CREATE  TABLE lieu ( 
	id_lieu              integer DEFAULT nextval('lieu_id_lieu_seq'::regclass) NOT NULL  ,
	nom_lieu             varchar(255)    ,
	id_type_lieu_fk      integer    ,
	CONSTRAINT pk_lieu PRIMARY KEY ( id_lieu ),
	CONSTRAINT fk_lieu_lieu FOREIGN KEY ( id_type_lieu_fk ) REFERENCES type_lieu( id_type_lieu )   
 );

CREATE  TABLE voyage ( 
	id_voyage            integer DEFAULT nextval('voyage_id_voyage_seq'::regclass) NOT NULL  ,
	nom_voyage           varchar    ,
	id_lieu_fk           integer    ,
	id_bouquet_fk        integer    ,
	CONSTRAINT pk_voyage PRIMARY KEY ( id_voyage ),
	CONSTRAINT fk_voyage_bouquet FOREIGN KEY ( id_bouquet_fk ) REFERENCES bouquet( id_bouquet )   ,
	CONSTRAINT fk_voyage_lieu FOREIGN KEY ( id_lieu_fk ) REFERENCES lieu( id_lieu )   
 );

CREATE VIEW v_activite_bouquet AS SELECT e.id_bouquet,
    e.nom_bouquet,
    i.id_activite,
    i.nom_activite
   FROM ((activite_bouquet t
     JOIN bouquet e ON ((t.id_bouquet_fk = e.id_bouquet)))
     JOIN activite i ON ((t.id_activite_fk = i.id_activite)));

CREATE VIEW v_activite_voyage_with_durre_and_quantite AS SELECT activite_voyage.id_voyage_fk,
    activite_voyage.id_activite_fk,
    activite_voyage.id_durre_fk,
    count(*) AS quantite
   FROM activite_voyage
  GROUP BY activite_voyage.id_voyage_fk, activite_voyage.id_activite_fk, activite_voyage.id_durre_fk;

CREATE VIEW v_lieu_type AS SELECT u.id_type_lieu,
    u.nom_type_lieu,
    e.id_lieu,
    e.nom_lieu
   FROM (type_lieu u
     JOIN lieu e ON ((e.id_type_lieu_fk = u.id_type_lieu)));

CREATE VIEW v_voyage_all_prix AS SELECT v_voyage_nombre_activite.id_voyage,
    v_voyage_nombre_activite.id_activite,
    v_voyage_nombre_activite.nombre_activite,
    activite_prix.id_activite_fk,
    activite_prix.prix,
    activite_prix.etat
   FROM (v_voyage_nombre_activite
     JOIN activite_prix ON ((v_voyage_nombre_activite.id_activite = activite_prix.id_activite_fk)));

CREATE VIEW v_voyage_bouquet_activite AS SELECT voyage.id_voyage,
    voyage.nom_voyage,
    voyage.id_lieu_fk,
    v_activite_bouquet.id_bouquet,
    v_activite_bouquet.nom_bouquet,
    v_activite_bouquet.id_activite,
    v_activite_bouquet.nom_activite
   FROM (voyage
     JOIN v_activite_bouquet ON ((voyage.id_bouquet_fk = v_activite_bouquet.id_bouquet)));

CREATE VIEW v_voyage_durre AS SELECT activite_voyage.id_voyage_fk,
    activite_voyage.id_durre_fk
   FROM activite_voyage
  GROUP BY activite_voyage.id_voyage_fk, activite_voyage.id_durre_fk;

CREATE VIEW v_voyage_nombre_activite AS SELECT activite_voyage.id_voyage_fk AS id_voyage,
    activite_voyage.id_activite_fk AS id_activite,
    count(activite_voyage.id_activite_fk) AS nombre_activite
   FROM activite_voyage
  GROUP BY activite_voyage.id_voyage_fk, activite_voyage.id_activite_fk;

CREATE VIEW v_voyage_prix_actuelle AS SELECT v_voyage_all_prix.id_voyage,
    sum(((v_voyage_all_prix.id_activite)::double precision * v_voyage_all_prix.prix)) AS prix
   FROM v_voyage_all_prix
  WHERE (v_voyage_all_prix.etat = 10)
  GROUP BY v_voyage_all_prix.id_voyage;

CREATE VIEW voyage_prix_base AS SELECT v_voyage_bouquet_activite.id_voyage,
    v_voyage_bouquet_activite.nom_voyage,
    v_voyage_bouquet_activite.id_lieu_fk,
    v_voyage_bouquet_activite.id_bouquet,
    v_voyage_bouquet_activite.nom_bouquet,
    v_voyage_bouquet_activite.id_activite,
    v_voyage_bouquet_activite.nom_activite,
    activite_prix.prix,
    activite_prix.etat
   FROM (v_voyage_bouquet_activite
     JOIN activite_prix ON ((activite_prix.id_activite_fk = v_voyage_bouquet_activite.id_activite)));

INSERT INTO activite( id_activite, nom_activite ) VALUES ( 1, 'Football');
INSERT INTO activite( id_activite, nom_activite ) VALUES ( 2, 'Basket-ball');
INSERT INTO activite_prix( id_activite_fk, prix, etat ) VALUES ( 1, 3000.0, 5);
INSERT INTO activite_prix( id_activite_fk, prix, etat ) VALUES ( 1, 4000.0, 10);
INSERT INTO activite_prix( id_activite_fk, prix, etat ) VALUES ( 2, 4500.0, 5);
INSERT INTO activite_prix( id_activite_fk, prix, etat ) VALUES ( 2, 1000.0, 10);
INSERT INTO activite_voyage( id_voyage_fk, id_activite_fk, id_durre_fk ) VALUES ( 1, 2, 1);
INSERT INTO activite_voyage( id_voyage_fk, id_activite_fk, id_durre_fk ) VALUES ( 2, 2, 2);
INSERT INTO activite_voyage( id_voyage_fk, id_activite_fk, id_durre_fk ) VALUES ( 2, 2, 1);
INSERT INTO activite_voyage( id_voyage_fk, id_activite_fk, id_durre_fk ) VALUES ( 2, 2, 2);
INSERT INTO activite_voyage( id_voyage_fk, id_activite_fk, id_durre_fk ) VALUES ( 2, 2, 2);
INSERT INTO activite_voyage( id_voyage_fk, id_activite_fk, id_durre_fk ) VALUES ( 5, 2, 1);
INSERT INTO activite_voyage( id_voyage_fk, id_activite_fk, id_durre_fk ) VALUES ( 5, 2, 1);
INSERT INTO activite_voyage( id_voyage_fk, id_activite_fk, id_durre_fk ) VALUES ( 5, 2, 3);
INSERT INTO activite_voyage( id_voyage_fk, id_activite_fk, id_durre_fk ) VALUES ( 5, 1, 1);
INSERT INTO activite_voyage( id_voyage_fk, id_activite_fk, id_durre_fk ) VALUES ( 5, 1, 2);
INSERT INTO activite_voyage( id_voyage_fk, id_activite_fk, id_durre_fk ) VALUES ( 5, 1, 2);
INSERT INTO billet_activite_entrant( id_activite_fk, quantite ) VALUES ( 1, 5);
INSERT INTO bouquet( id_bouquet, nom_bouquet ) VALUES ( 1, 'bouquet1');
INSERT INTO bouquet( id_bouquet, nom_bouquet ) VALUES ( 2, 'Premium');
INSERT INTO durre( id_duree, nom_duree ) VALUES ( 1, 'Court');
INSERT INTO durre( id_duree, nom_duree ) VALUES ( 2, 'Moyen');
INSERT INTO durre( id_duree, nom_duree ) VALUES ( 3, 'Long');
INSERT INTO type_lieu( id_type_lieu, nom_type_lieu ) VALUES ( 1, 'Nationale');
INSERT INTO type_lieu( id_type_lieu, nom_type_lieu ) VALUES ( 2, 'International');
INSERT INTO activite_bouquet( id_activite_fk, id_bouquet_fk ) VALUES ( 1, 1);
INSERT INTO activite_bouquet( id_activite_fk, id_bouquet_fk ) VALUES ( 2, 2);
INSERT INTO activite_bouquet( id_activite_fk, id_bouquet_fk ) VALUES ( 2, 1);
INSERT INTO activite_bouquet( id_activite_fk, id_bouquet_fk ) VALUES ( 1, 2);
INSERT INTO lieu( id_lieu, nom_lieu, id_type_lieu_fk ) VALUES ( 1, 'Toamasina', 1);
INSERT INTO lieu( id_lieu, nom_lieu, id_type_lieu_fk ) VALUES ( 2, 'Antananarivo', 1);
INSERT INTO lieu( id_lieu, nom_lieu, id_type_lieu_fk ) VALUES ( 3, 'Paris', 2);
INSERT INTO voyage( id_voyage, nom_voyage, id_lieu_fk, id_bouquet_fk ) VALUES ( 1, '2', 1, 2);
INSERT INTO voyage( id_voyage, nom_voyage, id_lieu_fk, id_bouquet_fk ) VALUES ( 2, 'Vacance de Noel', 1, 2);
INSERT INTO voyage( id_voyage, nom_voyage, id_lieu_fk, id_bouquet_fk ) VALUES ( 3, 'Vacance de Noel', 1, 1);
INSERT INTO voyage( id_voyage, nom_voyage, id_lieu_fk, id_bouquet_fk ) VALUES ( 4, 'Partie du fin anne', 3, 2);
INSERT INTO voyage( id_voyage, nom_voyage, id_lieu_fk, id_bouquet_fk ) VALUES ( 5, 'Voyage du sud', 2, 2);

distinct