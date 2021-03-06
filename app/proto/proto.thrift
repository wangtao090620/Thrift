 namespace java com.proto.protocol

 enum Code {
     OK = 0,
     False = 1,
     ParamError = 2,
     OutBounds = 3,
     Unknown = 4,
     NetworkError = 5,
     NotSupported = 6,
     ApiLowVersion = 7,
     Duplicate = 10,
     Already = 11,
     NotExist = 12,
     IsProtected = 13,
     NotReady = 14,
     NotEnough = 15,
     LoginError = 100,
     LoginLowVersion = 101,
     LoginDuplicate = 102,
     LoginAlready = 103,
     Blocked = 104,
     ServerError = 110,
     ServerBusy = 111,
     Expired = 120,
     NotChanged = 1102,
 }

 struct Results{
    1: string createdAt,
    2: string type,
    3: string url,
    4: string who
 }

 struct ImageRequest{
    1: string url;
 }

 struct ImageResponse{
    1: list<Results> results
 }


 service ImeService{

    ImageResponse loadImage(1:ImageRequest request)

 }