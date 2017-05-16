package com.nantes.polytech.netapsys.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Aurelie on 21/12/2016.
 */

@Entity
public class Inscription {

    @Id (autoincrement = true) private Long id;
    private Long memberId;
    private Long activityId;

    @ToOne(joinProperty = "memberId")
    private Member member;

    @ToOne(joinProperty = "activityId")
    private Activity activity;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1281644211)
    private transient InscriptionDao myDao;
    @Generated(hash = 336831450)
    private transient Long member__resolvedKey;
    @Generated(hash = 998340000)
    private transient Long activity__resolvedKey;

    public Inscription(Activity activity, Member member) {
        this.activity = activity;
        this.member = member;
    }

    @Generated(hash = 2074151904)
    public Inscription(Long id, Long memberId, Long activityId) {
        this.id = id;
        this.memberId = memberId;
        this.activityId = activityId;
    }

    @Generated(hash = 1505597554)
    public Inscription() {
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "activity=" + activity +
                ", id=" + id +
                ", memberId=" + memberId +
                ", activityId=" + activityId +
                ", member=" + member +
                '}';
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1904522975)
    public Member getMember() {
        Long __key = this.memberId;
        if (member__resolvedKey == null || !member__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MemberDao targetDao = daoSession.getMemberDao();
            Member memberNew = targetDao.load(__key);
            synchronized (this) {
                member = memberNew;
                member__resolvedKey = __key;
            }
        }
        return member;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 434444440)
    public void setMember(Member member) {
        synchronized (this) {
            this.member = member;
            memberId = member == null ? null : member.getId();
            member__resolvedKey = memberId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1412128217)
    public Activity getActivity() {
        Long __key = this.activityId;
        if (activity__resolvedKey == null || !activity__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ActivityDao targetDao = daoSession.getActivityDao();
            Activity activityNew = targetDao.load(__key);
            synchronized (this) {
                activity = activityNew;
                activity__resolvedKey = __key;
            }
        }
        return activity;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1100757865)
    public void setActivity(Activity activity) {
        synchronized (this) {
            this.activity = activity;
            activityId = activity == null ? null : activity.getId();
            activity__resolvedKey = activityId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1781227086)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInscriptionDao() : null;
    }
}
