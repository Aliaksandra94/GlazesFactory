package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.GlazesType;
import com.project.moroz.glazes_market.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface GlazesTypeDAO extends JpaRepository<GlazesType, Integer> {
    GlazesType findByName(String name);


//    @Query(value = "select g.recipe from GlazesType g where g.id =:id")
//    Map<RawMaterial, Double> getGlazesTypePrice(int id);
}
//@Query(value = "from Item it where it not in (select i from Item i " +
//        "join i.commercialObjectQuantityOfItems co where co.commercialObject.id = :idCommercialObject)")
//
//
//@Query("from Order where shift.id in (select sh.id from Shift sh where " +
//        "sh.dateOpened = :date and sh.commercialObject.id = :comId and sh.employee.id = :idEmployee)")
//
//@Query(value = "select sum(card_amount + cash_amount) as total, sum(card_amount) as card, sum(cash_amount) as cash," +
//        " (select count(shift_id) from orders where date_order between :fromDate and :toDate and shift_id in" +
//        " (select id from shift where date_opened between :fromDate and :toDate and " +
//        "commercial_object_id = :idCommercialObject) and (cash_amount != '0' or card_amount !='0')) as countOrder," +
//        " (select count(shift_id) from orders where date_order between :fromDate and :toDate and shift_id " +
//        "in (select id from shift where date_opened between :fromDate and :toDate and " +
//        "commercial_object_id = :idCommercialObject) and cash_amount = '0' and card_amount ='0') as countOrderCanceled" +
//        " from orders where date_order between :fromDate and :toDate and shift_id " +
//        "in (select id from shift where date_opened between :fromDate and :toDate " +
//        "and commercial_object_id = :idCommercialObject)", nativeQuery = true)