#define DEBUG 0
#include <jni.h>

static jobject occ_downcast(JNIEnv* env, opencascade::handle<Standard_Transient> p) {
        std::string klassName = std::string("occ/TKernel$")+std::string(p->DynamicType()->Name());
        if (DEBUG) std::cerr << "Try to find class " <<  klassName << std::endl;
        jclass jklass = NULL;
        if (env->PushLocalFrame(1) != 0) {
                std::cerr << "Error PushLocalFrame(1) returned error " <<  klassName.c_str() << std::endl;
                }
        jclass cls = env->FindClass(klassName.c_str());
        if (cls == NULL || env->ExceptionCheck()) {
                std::cerr << "Error loading class " <<  klassName.c_str() << std::endl;
                return NULL;
        }
        jklass = (jclass)env->NewWeakGlobalRef(cls);
        if (jklass == NULL || env->ExceptionCheck()) {
                std::cerr << "Error creating class " <<  klassName  << std::endl;
                return NULL;
        }
        env->PopLocalFrame(NULL);

        if (DEBUG) std::cerr << "Try to call constructor for class " <<  klassName << std::endl;

        jmethodID mid = env->GetMethodID(jklass, "<init>", "(Lorg/bytedeco/javacpp/Pointer;)V");
        if (mid == NULL || env->ExceptionCheck()) {
                std::cerr <<  "Error getting Pointer constructor, while VM does not support AllocObject()" <<  klassName  << std::endl;
                return NULL;
                }
        if (DEBUG) std::cerr << "Success! " <<  klassName << std::endl;


        jobject obj = env->NewObject(jklass, mid, NULL);

        return obj;
}

template <typename T>
using mysmart_ptr = opencascade::handle<T>;

template<class T> class occ_smart_ptr_adpter {
public:
    occ_smart_ptr_adpter(const T* ptr, int size, void *owner) :
        ptr((T*)ptr),
        size(size),
        owner(owner),
        smartPtr2(owner != NULL && owner != ptr ? *(mysmart_ptr<T>*)owner : mysmart_ptr<T>((T*)ptr)),
        smartPtr(smartPtr2) { }
    occ_smart_ptr_adpter(const mysmart_ptr<T>& smartPtr) :
        ptr(0),
        size(0),
        owner(0),
        smartPtr2(smartPtr),
        smartPtr(smartPtr2) { }
    void assign(T* ptr, int size, void* owner) {
        this->ptr = ptr;
        this->size = size;
        this->owner = owner;
        this->smartPtr = owner != NULL && owner != ptr ? *(mysmart_ptr<T>*)owner : mysmart_ptr<T>((T*)ptr);
    }
    static void deallocate(void* owner) {
        delete (mysmart_ptr<T>*)owner;
    }
    operator T*() {
        ptr = smartPtr.get();
        if (owner == NULL || owner == ptr) {
            owner = new mysmart_ptr<T>(smartPtr);
        }
        return ptr;
    }
    operator mysmart_ptr<T>&() {
        return smartPtr;
    }
    operator mysmart_ptr<T>*() {
        return ptr ? &smartPtr : 0;
    }

    jobject createPointer(JNIEnv* env) {
        return occ_downcast(env,smartPtr);
        }

    T* ptr;
    int size;
    void* owner;
    mysmart_ptr<T> smartPtr2;
    mysmart_ptr<T>& smartPtr;
};
