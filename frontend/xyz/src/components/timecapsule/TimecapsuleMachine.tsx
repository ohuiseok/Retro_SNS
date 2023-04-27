'use client'

import React, { useState } from 'react';
import Image from "next/image";
import Modal from '../Modal';
import AbleTimecaplsuleModal from './AbleTimecaplsuleModal';

export default function TimecapsuleMachine() {
  const [isModal, setIsModal] = useState(false);

  const handleClick = () => {
    setIsModal(true);
  };
  return (
    <div>
      <div className="flex flex-col items-center justify-center">
        <div className="mb-4">
          <Image
            src="/images/machine.svg"
            alt="machine"
            width={320}
            height={140}
          />
        </div>
        <div onClick={handleClick} >
        <Image
            src="/images/capsule.svg"
            alt="capsule"
            width={100}
            height={100}
          />
        </div>
        {/* 캡슐 잠김 여부에 따라  AbleTimecaplsuleModal or UnAbleTimecaplsuleModal*/}
        {isModal && <Modal closeModal={() => setIsModal(false)}>
            <AbleTimecaplsuleModal /></Modal>}
      </div>
    </div>
  );
}
