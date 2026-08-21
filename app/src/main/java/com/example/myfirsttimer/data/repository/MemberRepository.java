package com.example.myfirsttimer.data.repository;

import com.example.myfirsttimer.data.dao.MemberDao;
import com.example.myfirsttimer.data.entity.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    private final MemberDao dao;

    public MemberRepository(MemberDao dao) {
        this.dao = dao;
    }

    public long insert(Member member) {
        return dao.insert(member);
    }

    public void update(Member member) {
        dao.update(member);
    }

    public Member getById(long id) {
        return dao.getById(id);
    }

    public Member getByPhone(String phone) {
        return dao.getByPhone(phone);
    }

    public List<Member> search(String query) {
        return dao.search(query);
    }

    public List<Member> searchAutocomplete(String query) {
        if (query == null || query.trim().isEmpty()) return new ArrayList<>();
        return dao.searchAutocomplete(query.trim());
    }

    public List<Member> getAll() {
        return dao.getAll();
    }
}
