export interface UserTypes {
  userSeq: number;
  nickname: string;
  profileImage: string;
}

export interface BgmTypes {
  bgmTitle: string;
  bgmLink: string;
}

// 프로필 메인
export interface UserProfileType {
  nickname: string;
  visitCount: number;
  backgroundImage: string;
  profileImage: string;
  introduce: string;
  modifier: string;
  modifierColor: string;
  modifierGrade: string;
  identify: string;
  friend: boolean;
  friendRequest: boolean;
  friendResponse: boolean;
  friendTime: number;
  bgms: BgmTypes[];
}

// 방명록
export interface VisitorTypes {
  userSeq: number;
  visitorSeq: number;
  nickname: string;
  profileImage: string;
  modifier: string;
  modifierColor: string;
  modifierGrade: string;
  content: string;
  createdTime: string;
}

//마이포토
export interface MyPhotoTypes {
  data: string;
}

//마이포토 필터
export interface MyPhotoFilterTypes {
  data: number;
}
