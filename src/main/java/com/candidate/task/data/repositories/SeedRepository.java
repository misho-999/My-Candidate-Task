package com.candidate.task.data.repositories;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class SeedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void seedPeople() {
        this.entityManager.createNativeQuery("INSERT INTO t_people (FULL_NAME, PIN) VALUES\n" +
                "('Ivan Ivanov', '1234567890'),\n" +
                "('Ivan Ivanov', 'JAKSLD8979'),\n" +
                "('Ivan Ivanov', 'GDG454DK58'),\n" +
                "('Михаил Ангелов', '9999999999'),\n" +
                "('Gosho-Goshev', '1234567890'),\n" +
                "('Gosho-Goshev', '789DKSJA78'),\n" +
                "('Canko Georgiev','9805256053'),\n" +
                "('Gaco Bacov','FR05256053'),\n" +
                "('Dragan Petkanov', '1234567890');\n")
                .executeUpdate();
    }

    @Transactional
    public void seedEmails() {
        this.entityManager.createNativeQuery("INSERT INTO t_mails (EMAIL, EMAIL_TYPE, T_PEOPLE_ID) VALUES \n" +
                "('misho_angelov@yahoo.com', 'Mobil', 1),\n" +
                "('Ivan_Ivanov@yahoo.com', 'Lead', 2),\n" +
                "('ivan@yivan.gmail.com', 'Promo', 3),\n" +
                "('gosho125@abv.bg', 'WELC', 4),\n" +
                "('gosho_a@mail.bg','WELC', 5),\n" +
                " ('pesho@amazon.uk', 'Promo', 6),\n" +
                " ('pesho@yahoo.com', 'Promo', 7),\n" +
                " ('abv@abv.com', 'Promo', 8),\n" +
                "('drago_picha@mail.uk', 'Lead', 9);")
                .executeUpdate();
    }

    @Transactional
    public void seedAddresses() {
        this.entityManager.createNativeQuery("INSERT INTO t_addresses (ADDR_TYPE, ADDR_INFO, T_PEOPLE_ID) VALUES" +
                "('PERM', 'Sofia, Luilin 9, bl 952', 1),\n" +
                "('TEMP', 'Plovdiv, Trakia, bl 152', 2),\n" +
                "('TEMP', 'Sofia, Belite Brezi 25', 3),\n" +
                "('PERM', 'Sofia, Ivan Vazov 25', 4),\n" +
                "('PERM', 'Varna, Vladislavovo, bl 88', 5),\n" +
                "('TEMP', 'Burgas, Meden Rudnik, bl 23', 6),\n" +
                "('TEMP', 'Ruse, Lebed, 65A', 7),\n" +
                "('TEMP', 'Vratsa, Chaika, 20B', 8),\n" +
                "('PERM', 'Sara zagora, Trite Chuchura 55', 9);")
                .executeUpdate();
    }

    @Transactional
    public void createMail(Long id, String emailType, String email) {
        this.entityManager.createNativeQuery("INSERT INTO t_mails (EMAIL, EMAIL_TYPE, T_PEOPLE_ID) " +
                "VALUES (?, ?, ?)")
                .setParameter(1, email)
                .setParameter(2, emailType)
                .setParameter(3, id)
                .executeUpdate();
    }

    @Transactional
    public void createAddress(Long id, String addressType, String addressInfo) {
        this.entityManager.createNativeQuery("INSERT INTO t_addresses (ADDR_TYPE, ADDR_INFO, T_PEOPLE_ID) VALUES (?, ?, ?)")
                .setParameter(1, addressType)
                .setParameter(2, addressInfo)
                .setParameter(3, id)
                .executeUpdate();
    }
}
