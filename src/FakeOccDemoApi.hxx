
#include <iostream>
#include "Standard_Transient.hxx"
#include "Standard_Handle.hxx"

#include "occ-handle-adapter.hxx"

class Example_Base;
DEFINE_STANDARD_HANDLE(Example_Base,Standard_Transient)

class Example_Base : public Standard_Transient {
        DEFINE_STANDARD_RTTI_INLINE(Example_Base,Standard_Transient)
        public:
                virtual Handle(Example_Base) foobar(Handle(Example_Base) p) {
                        return 0;
                        };
        };

class Example_Derived;
DEFINE_STANDARD_HANDLE(Example_Derived,Example_Base)

opencascade::handle<Example_Base> someFoo(Handle(Example_Base) p) {return p;};

class Example_Derived : public Example_Base {
        DEFINE_STANDARD_RTTI_INLINE(Example_Derived,Example_Base)
        public:
                virtual Handle(Example_Base)  foobar(Handle(Example_Base) p) override {
                        return someFoo(p);
                        };
        private:
                Handle(Example_Base) member; // not used
        };
