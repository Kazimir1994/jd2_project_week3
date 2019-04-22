package ru.kazimir.bortnik.springbootmodule.servicedata.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kazimir.bortnik.springbootmodule.repository.AuditItemRepository;
import ru.kazimir.bortnik.springbootmodule.repository.model.AuditItem;
import ru.kazimir.bortnik.springbootmodule.repository.model.Item;

import java.sql.Connection;
import java.sql.Timestamp;

@Aspect
@Component
public class Advice {
    private static final Logger logger = LoggerFactory.getLogger(Advice.class);

    private final AuditItemRepository auditItemRepository;

    @Autowired
    public Advice(AuditItemRepository auditItemRepository) {
        this.auditItemRepository = auditItemRepository;
    }

    @Pointcut("execution(* ru.kazimir.bortnik.springbootmodule.repository.ItemsRepository.add(..))")
    public void createStatusPointCut() {
    }

    @AfterReturning(value = "createStatusPointCut()", returning = "savedItem")
    public void logAddingItem(JoinPoint joinPoint, Item savedItem) {

        AuditItem auditItem = new AuditItem();
        auditItem.setAction("ADDED");
        auditItem.setDate(new Timestamp(System.currentTimeMillis()));
        auditItem.setItemID(savedItem.getId());
        auditItem.setUpdateStatus(savedItem.getStatus());

        Connection connection = (Connection) joinPoint.getArgs()[1];
        auditItemRepository.add(connection, auditItem);
        logger.info("Create Item" + auditItem.toString());
    }

    @Pointcut("execution(* ru.kazimir.bortnik.springbootmodule.repository.ItemsRepository.update(..))")
    public void updatingStatusPointCut() {
    }

    @AfterReturning(value = "updatingStatusPointCut()", returning = "sizeUpdate")
    public void logStatusUpdate(JoinPoint joinPoint, int sizeUpdate) {
        if (sizeUpdate == 1) {
            AuditItem auditItem = new AuditItem();
            auditItem.setAction("UPDATED");
            auditItem.setDate(new Timestamp(System.currentTimeMillis()));
            auditItem.setItemID(Long.parseLong(joinPoint.getArgs()[0].toString()));
            auditItem.setUpdateStatus(String.valueOf(joinPoint.getArgs()[1]));

            Connection connection = (Connection) joinPoint.getArgs()[2];
            auditItemRepository.add(connection, auditItem);
            logger.info("Update Item" + auditItem.toString());
        }
    }
}
