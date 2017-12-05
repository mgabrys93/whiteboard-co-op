package com.example.app.whiteboardcoop.service.impl;

import com.example.app.whiteboardcoop.model.User;
import com.example.app.whiteboardcoop.model.Whiteboard;
import com.example.app.whiteboardcoop.repository.WhiteboardRepository;
import com.example.app.whiteboardcoop.service.WhiteboardService;
import com.example.app.whiteboardcoop.util.Constants;
import com.example.app.whiteboardcoop.util.PaginationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WhiteboardServiceImpl implements WhiteboardService {

    @Autowired
    private PaginationModel paginationModel;

    @Autowired
    private WhiteboardRepository whiteboardRepository;

    @Override
    public void save(Whiteboard whiteboard) {
        whiteboardRepository.save(whiteboard);
    }

    @Override
    public Optional<Whiteboard> findOne(Long id) {
        return Optional.ofNullable(whiteboardRepository.findOne(id));
    }

    @Override
    public List<Whiteboard> findAll() {
        return whiteboardRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        whiteboardRepository.delete(id);
    }

    @Override
    public List<Whiteboard> getNewestInactiveWhiteboards(int page) {
        PageRequest pageRequest = new PageRequest(page, Constants.PAGE_ELEMENTS, Sort.Direction.DESC, "creationDateTime");
        return whiteboardRepository.findByActiveFalse(pageRequest);
    }

    @Override
    public List<Whiteboard> getNewestActiveWhiteboards(int page) {
        PageRequest pageRequest = new PageRequest(page, Constants.PAGE_ELEMENTS, Sort.Direction.DESC, "creationDateTime");
        return whiteboardRepository.findByActiveTrue(pageRequest);
    }

    @Override
    public void update(Whiteboard whiteboard) {
        whiteboardRepository.save(whiteboard);
    }

    @Override
    public List<Whiteboard> getNewestInactiveWhiteboardsByUser(User user, int page) {
        PageRequest pageRequest = new PageRequest(page, Constants.PAGE_ELEMENTS, Sort.Direction.DESC, "creationDateTime");
        return whiteboardRepository.findByOwnerAndActiveFalse(user, pageRequest);
    }

    @Override
    public List<Whiteboard> getNewestActiveWhiteboardsByUser(User user, int page) {
        PageRequest pageRequest = new PageRequest(page, Constants.PAGE_ELEMENTS, Sort.Direction.DESC, "creationDateTime");
        return whiteboardRepository.findByOwnerAndActiveTrue(user, pageRequest);
    }

    @Override
    public int getMaxActivePagesNumber() {
        return paginationModel.getPageNumberRoundedUp(whiteboardRepository.countAllByActiveTrue(), Constants.PAGE_ELEMENTS);
    }

    @Override
    public int getMaxInactivePagesNumber() {
        return paginationModel.getPageNumberRoundedUp(whiteboardRepository.countAllByActiveFalse(), Constants.PAGE_ELEMENTS);
    }

    @Override
    public int getMaxActivePagesNumberPerUser(User user) {
        return paginationModel.getPageNumberRoundedUp(whiteboardRepository.countAllByActiveTrueAndOwner(user), Constants.PAGE_ELEMENTS);
    }

    @Override
    public int getMaxInactivePagesNumberPerUser(User user) {
        return paginationModel.getPageNumberRoundedUp(whiteboardRepository.countAllByActiveFalseAndOwner(user), Constants.PAGE_ELEMENTS);
    }
}
