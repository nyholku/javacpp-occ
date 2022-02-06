#define DEBUG 0
#include <jni.h>

#ifndef __JAVACPP__

template <typename T>
using occ_smart_ptr = opencascade::handle<T>;

template<class T> class occ_smart_ptr_adpter {
public:
    occ_smart_ptr_adpter(const T* ptr, int size, void *owner) :
        ptr((T*)ptr),
        size(size),
        owner(owner),
        smartPtr2(owner != NULL && owner != ptr ? *(occ_smart_ptr<T>*)owner : occ_smart_ptr<T>((T*)ptr)),
        smartPtr(smartPtr2) { }
    occ_smart_ptr_adpter(const occ_smart_ptr<T>& smartPtr) :
        ptr(0),
        size(0),
        owner(0),
        smartPtr2(smartPtr),
        smartPtr(smartPtr2) { }
    void assign(T* ptr, int size, void* owner) {
        this->ptr = ptr;
        this->size = size;
        this->owner = owner;
        this->smartPtr = owner != NULL && owner != ptr ? *(occ_smart_ptr<T>*)owner : occ_smart_ptr<T>((T*)ptr);
    }
    static void deallocate(void* owner) {
        delete (occ_smart_ptr<T>*)owner;
    }
    operator T*() {
        ptr = smartPtr.get();
        if (owner == NULL || owner == ptr) {
            owner = new occ_smart_ptr<T>(smartPtr);
        }
        return ptr;
    }
    operator occ_smart_ptr<T>&() {
        return smartPtr;
    }
    operator occ_smart_ptr<T>*() {
        return ptr ? &smartPtr : 0;
    }

    jobject createPointer(JNIEnv* env) {
        return occ_downcast(env,smartPtr);
        }

    T* ptr;
    int size;
    void* owner;
    occ_smart_ptr<T> smartPtr2;
    occ_smart_ptr<T>& smartPtr;
};
#endif
