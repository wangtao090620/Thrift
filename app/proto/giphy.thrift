namespace java com.giphy.protocol

struct FixedWidthEntity {
	1:string url;
	2:string mp4;
}

struct ImagesEntity {
	1:FixedWidthEntity fixed_width;
}

struct Data {
	1:ImagesEntity images;
}

struct Meta {
    1: i32 status;
    2: string msg;
}

struct Pagination {
    1: i32 total_count;
    2: i32 count;
    3: i32 offset;
}

struct SearchRequest {
    1: string q;
    2: string api_key;
}

struct SearchResponse {
    1: list<Data> data;
    2: Meta meta;
    3: Pagination pagination;
}

service GiphyService {

    SearchResponse search(1: SearchRequest req);
}