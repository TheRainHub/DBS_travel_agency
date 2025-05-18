//package DAO;
//
//import entities.Language;
//import jakarta.persistence.EntityManager;
//import java.util.Optional;
//
//public class LanguageDao extends BaseDao<Language> {
//
//    public LanguageDao(EntityManager em) {
//        super(em, Language.class);
//    }
//
//    @Override
//    public Language findById(Object id) {          // тип Object → можно передавать String
//        return super.findById(id);
//    }
//}
