import React from 'react';
import { Translate } from 'react-jhipster';
import { useAppSelector } from 'app/config/store';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  const account = useAppSelector(state => state.authentication.account);
  const isAdmin = account?.authorities?.includes('ROLE_ADMIN');
  const isRecruiter = account?.authorities?.includes('ROLE_RECRUITER');
  const isTrainingProvider = account?.authorities?.includes('ROLE_TRAINING_PROVIDER');
  const isUser = account?.authorities?.includes('ROLE_USER');

  return (
    <>
      {/* User/Candidate Menu - Only Exam Sessions */}
      {isUser && !isRecruiter && !isTrainingProvider && !isAdmin && (
        <>
          <MenuItem icon="play-circle" to="/exam-session">
            <Translate contentKey="global.menu.entities.examSession" />
          </MenuItem>
        </>
      )}

      {/* Recruiter Menu - Create Recruitment Exams */}
      {isRecruiter && (
        <>
          <MenuItem icon="file-alt" to="/exam">
            <Translate contentKey="global.menu.entities.exam" />
          </MenuItem>
          <MenuItem icon="tasks" to="/exercise">
            <Translate contentKey="global.menu.entities.exercise" />
          </MenuItem>
          <MenuItem icon="question-circle" to="/question">
            <Translate contentKey="global.menu.entities.question" />
          </MenuItem>
        </>
      )}

      {/* Training Provider Menu - Create Placement Tests & Certifications */}
      {isTrainingProvider && (
        <>
          <MenuItem icon="file-alt" to="/exam">
            <Translate contentKey="global.menu.entities.exam" />
          </MenuItem>
          <MenuItem icon="tasks" to="/exercise">
            <Translate contentKey="global.menu.entities.exercise" />
          </MenuItem>
          <MenuItem icon="question-circle" to="/question">
            <Translate contentKey="global.menu.entities.question" />
          </MenuItem>
        </>
      )}

      {/* Admin Menu - Full Access */}
      {isAdmin && (
        <>
          <MenuItem icon="user-tie" to="/recruiter">
            <Translate contentKey="global.menu.entities.recruiter" />
          </MenuItem>
          <MenuItem icon="user-graduate" to="/candidate">
            <Translate contentKey="global.menu.entities.candidate" />
          </MenuItem>
          <MenuItem icon="chalkboard-teacher" to="/training-provider">
            <Translate contentKey="global.menu.entities.trainingProvider" />
          </MenuItem>
          <MenuItem icon="file-alt" to="/exam">
            <Translate contentKey="global.menu.entities.exam" />
          </MenuItem>
          <MenuItem icon="tasks" to="/exercise">
            <Translate contentKey="global.menu.entities.exercise" />
          </MenuItem>
          <MenuItem icon="question-circle" to="/question">
            <Translate contentKey="global.menu.entities.question" />
          </MenuItem>
          <MenuItem icon="photo-video" to="/media">
            <Translate contentKey="global.menu.entities.media" />
          </MenuItem>
          <MenuItem icon="list" to="/answer-option">
            <Translate contentKey="global.menu.entities.answerOption" />
          </MenuItem>
          <MenuItem icon="edit" to="/fill-in-the-blank">
            <Translate contentKey="global.menu.entities.fillInTheBlank" />
          </MenuItem>
          <MenuItem icon="link" to="/bind-list">
            <Translate contentKey="global.menu.entities.bindList" />
          </MenuItem>
          <MenuItem icon="play-circle" to="/exam-session">
            <Translate contentKey="global.menu.entities.examSession" />
          </MenuItem>
          <MenuItem icon="clipboard-check" to="/candidate-answer">
            <Translate contentKey="global.menu.entities.candidateAnswer" />
          </MenuItem>
          <MenuItem icon="link" to="/bind-list-answer">
            <Translate contentKey="global.menu.entities.bindListAnswer" />
          </MenuItem>
        </>
      )}
      <MenuItem icon="asterisk" to="/recruiter">
        <Translate contentKey="global.menu.entities.recruiter" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/candidate">
        <Translate contentKey="global.menu.entities.candidate" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/training-provider">
        <Translate contentKey="global.menu.entities.trainingProvider" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/exam">
        <Translate contentKey="global.menu.entities.exam" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/exercise">
        <Translate contentKey="global.menu.entities.exercise" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/media">
        <Translate contentKey="global.menu.entities.media" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/exam-session">
        <Translate contentKey="global.menu.entities.examSession" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/candidate-answer">
        <Translate contentKey="global.menu.entities.candidateAnswer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/qcm-option">
        <Translate contentKey="global.menu.entities.qcmOption" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/blank-space">
        <Translate contentKey="global.menu.entities.blankSpace" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/list-item">
        <Translate contentKey="global.menu.entities.listItem" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/qcm-answer">
        <Translate contentKey="global.menu.entities.qcmAnswer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/blank-answer">
        <Translate contentKey="global.menu.entities.blankAnswer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bind-answer">
        <Translate contentKey="global.menu.entities.bindAnswer" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
