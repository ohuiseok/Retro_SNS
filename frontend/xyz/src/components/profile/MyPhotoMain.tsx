"use client";

import { useAppSelector } from "@/hooks/redux";
import { useMyPhoto } from "@/hooks/queries/myphoto";
import { useRouter } from "next/navigation";
import Btn from "../common/Btn";
import NotResultLottie from "@/components/lottie/NotResult";
import { useMyPhotoFilter } from "@/hooks/queries/myphoto";
import Img1 from "../../../public/images/background/bg (1).png";
import Img2 from "../../../public/images/background/bg (2).png";
import Img3 from "../../../public/images/background/bg (3).png";
import Img4 from "../../../public/images/background/bg (4).png";
import Img5 from "../../../public/images/background/bg (5).png";
import Img6 from "../../../public/images/background/bg (6).png";
import Img7 from "../../../public/images/background/bg (7).png";
import Img8 from "../../../public/images/background/bg (8).png";
import Img9 from "../../../public/images/background/bg (9).png";
import Img10 from "../../../public/images/background/bg (10).png";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { deleteMyPhoto } from "@/app/api/myphoto";
import { queryKeys } from "@/constants/queryKeys";
import LogoImg from "../../../public/images/logo.svg";
import Image from "next/image";

interface MyPhotoMainProps {
  userSeq: number;
}

function MyPhotoMain({ userSeq }: MyPhotoMainProps) {
  const images = [Img1, Img2, Img3, Img4, Img5, Img6, Img7, Img8, Img9, Img10];
  const router = useRouter();
  const state = useAppSelector((state) => state);
  const myUserSeq = state.auth.userInfo?.userSeq;
  const userSeqToNumber = +userSeq;
  const { data: myPhotoList, isLoading: isMyPhotoLoading } =
    useMyPhoto(userSeq);
  const { data: myPhotoFilter } = useMyPhotoFilter(userSeq);

  const PushToMyPhotoCreate = () => {
    router.push(`profile/${userSeq}/myphoto/create`);
  };
  const queryClient = useQueryClient();
  const useDeleteMyPhotoMutation = useMutation({
    mutationFn: () => deleteMyPhoto(),
    onSuccess: () => {
      queryClient.invalidateQueries(queryKeys.myroom.myroomList());
      alert("사진이 삭제되었습니다.");
    },
  });
  const DeleteMyPhoto = () => {
    useDeleteMyPhotoMutation.mutate();
  };

  const backgroundImgIdx = myPhotoFilter?.data;

  return (
    <div className=" w-full  border-black border-x border-b p-1">
      <div className="w-full ">
        {myPhotoFilter?.data && myPhotoList?.data ? (
          <div className=" w-full">
            <div className=" flex items-center justify-center object-cover relative">
              <img src={images[backgroundImgIdx!].src} />
              <div className="absolute p-5 max-w-[80%]">
                <img src={myPhotoList?.data} />
              </div>
              <div className="absolute">
                <Image
                  src={LogoImg}
                  alt="xyz 로고"
                  width="0"
                  height="0"
                  className="w-[80px] h-[24px]"
                />
              </div>
            </div>

            {myUserSeq === userSeqToNumber ? (
              <div className="flex items-center justify-center w-full py-5 gap-8">
                <Btn
                  btnFunc={PushToMyPhotoCreate}
                  bgColor="pink"
                  text="사진찍기"
                />
                <Btn btnFunc={DeleteMyPhoto} bgColor="blue" text="사진삭제" />
              </div>
            ) : null}
          </div>
        ) : (
          <div className="flex items-center justify-center w-full min-h-[300px]">
            <div className="flex flex-col justify-center py-2 w-full text-center">
              <NotResultLottie />
              대문 사진이 없어요ㅠㅠ
            </div>
            {myUserSeq === userSeqToNumber ? (
              <div className="flex items-center justify-center w-full pb-6">
                <Btn
                  btnFunc={PushToMyPhotoCreate}
                  bgColor="pink"
                  text="사진찍기"
                />
              </div>
            ) : null}
          </div>
        )}
      </div>
    </div>
  );
}

export default MyPhotoMain;
