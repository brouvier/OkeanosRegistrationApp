ALTER TABLE IF EXISTS adherent_info_saison ADD COLUMN IF NOT EXISTS fk_certificate_licence_id bigint AFTER fk_parental_agreement_id;
ALTER TABLE IF EXISTS adherent_info_saison ADD FOREIGN KEY (fk_certificate_licence_id) REFERENCES adherent_document(id);
